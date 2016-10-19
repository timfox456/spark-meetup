<link rel='stylesheet' href='../assets/main.css'/>


[LAB 1: Up and Running with Spark](./1-install-spark.md) 
[LAB 2: Shells](./2-shell.md) 
[LAB 3: Kmeans](./3-kmeans.md) 

Lab 2.1 : Up and Running With Spark
===================================

### Overview
We will be running Spark in a single node mode.

### Depends On 
None

### Run time
20 mins

---------------------
STEP 0: To Instructor
---------------------
Please go through this lab on 'screen' first.

--------------------------------
STEP 1: Login to your Spark node
--------------------------------
Instructor will provide details


--------------------------
STEP 2: 'Installing' Spark
--------------------------
There is no 'install'.  Just unzip/untar and run :-)
(copy paste the following commands on terminal,  do not include $ in your commands)

```bash
    $   cd
    $   rm -rf  spark   # cleanup existing spark installation (if any)
    $   tar xvf files/spark-1.6.1-bin-hadoop2.6.tgz
    $   mv spark-1.6.1-bin-hadoop2.6    spark
```

Now we have spark installed in  `~/spark`  directory


---------------------
STEP 3: Running Spark
---------------------

```bash
$   ~/spark/sbin/start-all.sh
```

Verify Spark is running by 'jps' command
```bash
    $  jps
```

Your output may look like this..
```console
  30624 Jps
  30431 Master
  30565 Worker
```
you will see **Master** and **Worker**  processes running.
(you probably will get different values for process ids - first column )

Spark UI will be at port 8080 of the host.
In browser go to
  http://your_spark_host_address:8080
(be sure to use the 'public' ip address)

bingo!  Now we have spark running.


--------------------------
STEP 4: Exploring Spark UI
--------------------------
You will see a similar screen shot like this

<img src="../images/1a.png" style="border: 5px solid grey ; max-width:100%;" /> 

To explore:
* Is Master and Worker running on the same node?

* Inspect memory & CPU available for Spark worker

* Note the Spark master URI, it will be something like
      spark://host_name:7077
    We will need this for later labs


--------------------------
STEP 5: Update spark-labs
--------------------------
Do the following to update the labs to latest
```bash
    $   cd ~/spark-labs
    $   git pull     # this will update the labs to latest
```

--------------------
Optional 1: Spark VM
--------------------

Here is a virtual machine for you, https://s3.amazonaws.com/elephantscale-public/vm/CentOS.ova

It is in the OVA format, useable both in VMWare and VirtualBox. 

Password: spark

--------------------
Optional 2: Windows
--------------------

If one has to do a Windows install, here is the magic

http://nishutayaltech.blogspot.com/2015/04/how-to-run-apache-spark-on-windows7-in.html

set winutils as described

For example

    \projects
    \projects\spark-labs
    \projects\spark-1.4.1-bin-hadoop2.4
    \projects\winutils\bin\winutils

System env variable

    HADOOP_HOME=\projects\winutils

With this download spark-1.4.1-bin-hadoop2.4
