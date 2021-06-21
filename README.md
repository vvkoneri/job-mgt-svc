Job Management Service built here allows you to submit jobs to be executed immediately or scheduled to be executed on a particular data/time. You can either submit single job for execution at a time or pass a job configuration file(xml) with jobs defined as a way to submit multiple jobs at a time. The code/task definition of a job should be coded using javascript/python.(Supported at this point). In case of Javascript it will be an embedded javascript code and in case of Python it will be path to the python script file.

 **COMMANDS**

  **addjob**
 
 -c,--code <arg>       code to be executed
  
 -e,--engine <arg>     scripting engine
  
 -f,--file <arg>       jobs file
  
 -n,--name <arg>       name of the job
  
 -o,--owner <arg>      owner of the job
  
 -p,--priority <arg>   job priority of execution
  
 -s,--schedule <arg>   schedule

  **removejob**
 
  -i,--id <arg>   job id
  

  **BUILD**

1. Build it with maven
   mvn clean install
   
2. Running the code
   mvn spring-boot:run
   


**SUBMIT JOBS**

There are two ways of submitting the jobs to the application
  1. Passing it as command parameters
  
       addjob --name job1 --owner venkatesh --engine javascript --code "print(100+1000)" --s immediate
  
       addjob --name job1 --owner venkatesh --engine python --code "src/main/resources/python/hello.py" --s immediate
                
  2. Passing it as configuration file
  
       addjob --file src/main/resources/examples/jobs.xml
     
**SAMPLE JOB CONFIGURATION FILE**  
  
  ```
  <?xml version="1.0" encoding="UTF-8"?>
  <jobs owner="testUser2">
    <job name="job1" engine="python" schedule="2021-06-18 14:20" priority="0">
      <![CDATA[src/main/resources/python/hello.py]]>
    </job>
    <job name="job2" engine="javascript" schedule="immediate" priority="2">
      <![CDATA[
        function hello() {
          print('Hello Payoneer Gang');
        }

        hello();
      ]]>
    </job>
    <job name="job3" engine="python" schedule="immediate" priority="9">
      <![CDATA[src/main/resources/python/hello.py]]>
    </job>
  </jobs>
  ```
  
**H2 Database**
1. Job queue is managed using H2 Database in app. On launch : http://localhost:9091/h2-console , username=rootAdmin, password=password123$


**POTENTIAL IMPROVEMENTS OR EXTENSIONS**
1. Support for executing PowerShell or Linux scripts (OS Based Scripting Support)
2. Including third type of scheduling feature called "recurring" which supports CRON schedules
3. Pub-Sub Messaging framework for job queueing to support scaling
4. Dedicated databased like Postgre
5. jasypt to encrypt credentials currently in properties file
6. Dedicated UI for Job Management and Control
7. Orchestration of jobs, executing dependant jobs in a sequential fashion and at a particular point
