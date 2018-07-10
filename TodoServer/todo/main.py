#!/usr/bin/env python

from http.server import BaseHTTPRequestHandler, HTTPServer
from os import path
from urllib.parse import urlparse
import json
import time


from tasksql import *


tasksql = Tasksql()

curdir = path.dirname(path.realpath(__file__))
sep = '/'


# HTTPRequestHandler class
class TasksRequestHandler(BaseHTTPRequestHandler):
    # GET
    def do_GET(self):
        self.handle_http_request()

    def isValidReq(self,token,ts):
        return True

    def handle_tasks_insert(self,args):
        print("handle_tasks_insert %r" %(args))
        dict1 = {'status':10001,'message':"error parameter"}
        if args is None:
            return dict1
        try :
            list = args['tasks']
            if list is None or len(list)==0:
                return dict1
            for item in list:
                print("item %r" %(item))
                task = Task(item['id'],item['title'],item['description'],item['completed'],item['ticks_expect'],item['ticks_consume'],item['father_id'],item['selected'],item['sub_ids'],item['create_time'],user_id=item['user_id'])
                tasksql.insert(task)
            dict = {'status':0,'message':'success'}
            return dict
        except Exception as e:
            print("e=%r" %(e))
            return dict1

    def filter_tasks(self,tasks_all,filter_arg):
        if tasks_all is None:
            return None
        if filter_arg is None:
            return tasks_all
        out = []

        print("-----")

        task_type = 1
        start_time = 0
        end_time = int(round(time.time() * 1000))
        user_id =0
        try:
            task_type = filter_arg['type']
            start_time = filter_arg['start_time']
            end_time = filter_arg['end_time']
            user_id = filter_arg['user_id']
        except Exception as e:
            print("filter e=%r" %(e))

        for item in tasks_all:
            '''
            print("item: %r" %(item.to_string()))
            if item.completed and task_type == 1:
                continue
            if not item.completed and task_type == 2:
                continue
            if item.create_time < start_time :
                continue
            if item.create_time > end_time:
                continue
            print(type(item.selected))
            '''
            if item.user_id == user_id :
                out.append(item)
        return out


    def handle_task_query(self,args):
        print("handle_tasks_query %r" %(args))
        dict1 = {'status':10001,'message':"error parameter"}
        if args is None:
            return dict1
        try :
            user_id = args['user_id']
            tasks_all = tasksql.select(user_id)
            print("tasks_all %r" %(tasks_all))
            out_tasks = self.filter_tasks(tasks_all,args)
            print("out tasks %r" %(out_tasks))

            dict = {'status':0,'message':'success'}
            dict['tasks'] = out_tasks
            return dict
        except Exception as e:
            print("e=%r" %(e))
            return dict1

    def handle_tasks_update(self,args):
        print("handle_tasks_update %r" %(args))
        dict1 = {'status':10001,'message':"error parameter"}
        if args is None:
            return dict1
        try :
            list = args['tasks']
            if list is None or len(list)==0:
                return dict1
            for item in list:
                task = Task(item['id'],item['title'],item['description'],item['completed'],item['ticks_expect'],item['ticks_consume'],item['father_id'],item['selected'],item['sub_ids'],item['create_time'],user_id=item['user_id'])
                tasksql.update(task)
            dict = {'status':0,'message':'success'}
            return dict
        except Exception as e:
            print("e=%r" %(e))
            return dict1

    def handle_tasks_del(self,args):
        print("handle_tasks_del %r" %(args))
        dict1 = {'status':10001,'message':"error parameter"}
        if args is None:
            return dict1
        try :
            list = args['tasks_id']
            if list is None or len(list)==0:
                return dict1
            for item in list:
                tasksql.delete(item)
            dict = {'status':0,'message':'success'}
            return dict
        except Exception as e:
            print("e=%r" %(e))
            return dict1


    def do_POST(self):
        print(self.path)
        action = self.path
        datas = self.rfile.read(int(self.headers['content-length']))

        args = json.loads(datas.decode())

        #print("data: %s" %(datas))
        if '?' in self.path:
            query =  urllib.splitquery(self.path)
            action = query[0]
        if action == '/test':
            buf='''{\"test\":\"HelloWorld\"}'''
        elif action == '/tasks_add':
            ret = self.handle_tasks_insert(args)
            buf = json.dumps(ret)
        elif action == '/tasks_query':
            ret = self.handle_task_query(args)
            buf = json.dumps(ret,default=lambda obj: obj.__dict__,sort_keys=True)
        elif action == '/tasks_update':
            ret = self.handle_tasks_update(args)
            buf = json.dumps(ret)
            
        elif action == '/tasks_del':
            ret = self.handle_tasks_del(args)
            buf = json.dumps(ret)
        else :
            buf='{}'

        print('response: %s' %(buf))
        self.send_response(200)
        mimetype = 'application/json'
        self.send_header('Content-type', mimetype)
        self.send_header('content-length', str(len(buf)))
        self.end_headers()
        self.wfile.write(buf.encode(encoding ="utf-8"))



def run():
    port = 8000
    print('starting server, port', port)

    # Server settings
    server_address = ('', port)
    httpd = HTTPServer(server_address, TasksRequestHandler)
    print('running server...')
    httpd.serve_forever()


if __name__ == '__main__':
    run()
