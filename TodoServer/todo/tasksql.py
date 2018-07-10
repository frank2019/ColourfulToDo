#encoding=utf-8
import sqlite3
import sys
from enum import Enum ,unique


@unique
class TaskType1(Enum):
    ALL_TASKS = 0
    ACTIVE_TASKS = 1
    COMPLETED_TASKS =2

class TaskType():
    def __init__(self):
        self.ALL_TASKS = 0
        self.ACTIVE_TASKS = 1
        self.COMPLETED_TASKS =2


filepath="test.db"

class Task :
    def __init__(self,id,title="",description='',completed=False,ticks_expect=0,ticks_consume=0,father_id=0,selected=False,sub_ids=None,create_time=0,user_id=0):
        self.id = id
        self.title=title
        self.description = description
        self.completed = completed
        self.ticks_expect = ticks_expect
        self.ticks_consume = ticks_consume
        self.father_id = father_id
        self.selected = selected
        self.sub_ids=sub_ids
        self.create_time = create_time
        self.user_id = user_id

    def to_string(self):
        return ("id=%r,title=%s,description=%s,user_id=%d,completed=%r,ticks_expect=%r" %(self.id,self.title,self.description,self.user_id,self.completed,self.ticks_expect))

def int_to_bool(value):
    if value is None:
        return False
    return value >0

# "1,4"  -->[1,4]
def to_int_array(str_sub_ids):
    if str_sub_ids is  None:
        return []
    arr = str_sub_ids.split(',')
    if arr is None or len(arr) == 0:
        return []
    sub_ids = []
    for item in arr:
        if item.isdigit():
            sub_ids.append(int(item))
    return sub_ids


class Tasksql:
    def __init__(self):
        self.conn=sqlite3.connect(filepath)
        self.cu = self.conn.cursor()  
    def create(self):  
        #self.cu.execute(("""create table user ( name varchar(50) primary key,  passwd varchar(50) UNIQUE )"""))          
        self.cu.execute('CREATE TABLE tasks( id VARCHAR  PRIMARY KEY , title VARCHAR, description  VARCHAR,completed    BOOLEAN, ticks_expect INT,ticks_consume  INT,father_id INT,selected BOOLEAN, sub_ids VARCHAR,create_time INTEGER ,user_id INTEGER)')

    def insert(self,task):
        print("task: %r" %(task.to_string()))
        
        sub_ids=""
        t =(task.id,task.title,task.description,task.completed,task.ticks_expect,task.ticks_consume,task.father_id,task.selected,task.sub_ids,task.create_time,task.user_id)

        print(t)

        self.cu.execute("insert into tasks (id,title,description,completed,ticks_expect,ticks_consume,father_id,selected,sub_ids,create_time,user_id)  values(?,?,?,?,?,?,?,?,?,?,?)",t)   
        self.conn.commit()  
        print('%s insert ' % (task.title))  
        return True  
    def update(self,task):
        t =(task.title,task.description,task.completed,task.ticks_expect,task.ticks_consume,task.father_id,task.selected,task.sub_ids,task.create_time,task.user_id,task.id)

        self.cu.execute("update tasks set title=?,description=?,completed=?,ticks_expect=?,ticks_consume=?,father_id=?,selected=?,sub_ids=?,create_time=?,user_id=? where id=?",t)   
        self.conn.commit()  
        print('%s update ' % (task.title))  
        return True  


    def select(self,user_id):  
        #t=(name,)       
        #self.cu.execute("select * from user where name =?",t)  
        cursor = self.cu.execute("select * from tasks where user_id=%d" %(user_id))
        list = []
        for row in cursor:
            str_sub_ids = to_int_array(row[8])

            task = Task(row[0],row[1],row[2],int_to_bool(row[3]),row[4],row[5],row[6],int_to_bool(row[7]),row[8],row[9],row[10])
            list.append(task)

        return list
    def delete(self,id):
        #self.cu.execute('DELETE from tasks where id=?',id)
        self.cu.execute('DELETE from tasks where id=%s' % id)
        #self.cu.execute('DELETE from tasks where id=1')
        self.conn.commit()

    def close(self):  
        self.conn.close()  

class OrmHandle(Tasksql):
    def register_user(self,user):
        t =(user.imei,user.uuid,user.nick_name,user.passwd,user.email,user.level,user.action,user.from_id)
        self.cu.execute("insert into users (imei,uuid,nick_name,passwd,email,level,action,from_id)  values(?,?,?,?,?,?,?,?)",t)   
        self.conn.commit()  
        print('%s insert ' % (user.nick_name))  
        return True  

    def table_create_users(self):  
        self.cu.execute('CREATE TABLE users( id INTEGER  PRIMARY KEY autoincrement, imei VARCHAR, uuid  VARCHAR, nick_name VARCHAR,passwd VARCHAR,email VARCHAR UNIQUE,level INT ,action INT ,from_id INT)')

    def update_user(self,user):
        try :
            t =(user.imei,user.uuid,user.nick_name,user.passwd,user.email,user.level,user.action,user.from_id,user.id)
            self.cu.execute("update users set imei=?,uuid=?,nick_name=?,passwd=?,email=?,level=?,action=?,from_id=? where id=?",t)   
            self.conn.commit()  
            print('%s update ' % (user.nick_name))  
            return True
        except Exception as e:
            print("update %s" %(e))
            return False

    def query_user(self,email):  
        #t=(name,)       
        #self.cu.execute("select * from user where name =?",t)  
        # cursor = self.cu.execute("select * from users where email = %s" %(email))
        cursor = self.cu.execute("select * from users where email = '%s'" %(email))
        for row in cursor:
            user = User(row[0],row[1],row[2],row[3],row[4],row[5],row[6],row[7],row[8])
            return user

        return None

class User :
    def __init__(self,id,imei=None,uuid=None,nick_name=None,passwd=None,email=None,level=0,action=0,from_id=0):
        self.id = id
        self.imei=imei
        self.uuid=uuid
        self.nick_name = nick_name
        self.passwd =passwd
        self.email = email
        self.level = level
        self.action = action
        self.from_id = from_id

    def __repr__(self):
        return 'User(id=%d,nick_name=%s,email=%s) ' %(self.id,self.nick_name,self.email)

def test_api_user():
    handle = OrmHandle()
    user = User(0,None,None,'frank2','123456','frank2019_yeah.net',1,0,0)
    user_new = User(1,None,None,'frank_new','888','frank2019_yeah.net',1,0,0)
    # test create_users table
    # handle.table_create_users()
    # handle.register_user(user)
    handle.update_user(user_new)
    user2 = handle.query_user('frank2019@yeah.net')
    print(user2)

def testsql3():

    task = Task("3",'test','test2',sub_ids="1,9",user_id=23)

    task_new = Task("3",'test8',"a8",True,2,sub_ids="2,55",user_id=23)

    tasksql = Tasksql()

    # tasksql.update(task_new)

    # tasksql.create()
    tasksql.insert(task)
    # tasksql.delete('3')


    list = tasksql.select(23)
    for i in list:
        print(" %s " %i.to_string())

def test_to_int_array():
    # s="1,4"
    s=None
    m=to_int_array(s)
    print(m)


if __name__ == "__main__":
    testsql3()
    # test_api_user()
    # test_to_int_array()
