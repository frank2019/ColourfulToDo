import requests
import time
import json

from tasksql import *


def test_api_test():
    url = 'http://localhost:8000/test'
    data='{}'
    r=requests.post(url,data=data)
    print("r=%r" %(r.text))

def test_api_insert():
    url = 'http://localhost:8000/tasks_add'
    tasks = []
    tasks.append(Task("2",'test1','dis',sub_ids='3,1',user_id=1))
    tasks.append(Task("1",'777','dis'))
    data={}
    data['tasks']=tasks
    data['token']="token111"
    data['ts']= int(round(time.time() * 1000))
 
    body = json.dumps(data,default=lambda obj: obj.__dict__,sort_keys=True)
    #body = json.dumps(data,default=lambda obj: obj.__dict__,sort_keys=True,indent =4)
    print('body %s' %(body) )

    r=requests.post(url,data=body)
    print("r=%r" %(r.text))
def test_api_query():
    url = 'http://localhost:8000/tasks_query'
    data={}
    data['type']=1
    data['user_id']=1
    data['token']="token111"
    data['ts']= int(round(time.time() * 1000))

    body = json.dumps(data)
    #body = json.dumps(data,default=lambda obj: obj.__dict__,sort_keys=True,indent =4)
    print('body %s' %(body) )

    r=requests.post(url,data=body)
    print("r=%r" %(r.text))

def test_api_update():
    url = 'http://localhost:8000/tasks_update'
    tasks = []
    tasks.append(Task("2",'6666','66666dis',user_id =1))
    data={}
    data['tasks']=tasks
    data['token']="token111"
    data['ts']= int(round(time.time() * 1000))
 
    body = json.dumps(data,default=lambda obj: obj.__dict__,sort_keys=True)
    #body = json.dumps(data,default=lambda obj: obj.__dict__,sort_keys=True,indent =4)
    print('body %s' %(body) )

    r=requests.post(url,data=body)
    print("r=%r" %(r.text))

def test_api_del():
    url = 'http://localhost:8000/tasks_del'
    tasks_id = []
    tasks_id.append('1')
    data={}
    data['tasks_id']=tasks_id
    data['token']="token111"
    data['ts']= int(round(time.time() * 1000))
 
    body = json.dumps(data,default=lambda obj: obj.__dict__,sort_keys=True)
    #body = json.dumps(data,default=lambda obj: obj.__dict__,sort_keys=True,indent =4)
    print('body %s' %(body) )

    r=requests.post(url,data=body)
    print("r=%r" %(r.text))
if __name__ == "__main__":
    #test_api_test()
    # test_api_insert()
    test_api_query()
    # test_api_update()
    test_api_del()
