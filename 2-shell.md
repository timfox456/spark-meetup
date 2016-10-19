<link rel='stylesheet' href='./assets/main.css'/>

[<< back to main index](../README.md)

Lab 2.2 : Spark Shell
===================

### Overview
Get familiar with Spark shell

### Builds on
[2.1-install-spark](2.1-install-spark.md)

### Run time
approx. 20-30 minutes

### Notes
Java / Scala users follow **Scala** section


--------------------
STEP 0:  Update labs
--------------------
Do the following to update the labs to latest
```bash
    $   cd ~/spark-labs
    $   git pull     # this will update the labs to latest
```


----------------------------
STEP 1:  Launch  Spark shell
----------------------------

Scala shell:
```bash
    $   cd ~/spark-labs
    $   ~/spark/bin/spark-shell  ## spark shell is in bin/ dir
```

Console output will look like

```
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 1.6.0
      /_/

Using Scala version 2.10.5 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_72)
Type in expressions to have them evaluated.
Type :help for more information.
Spark context available as sc.
...
scala>

```


--------------------------------
STEP 2: Exploring Spark shell UI
--------------------------------
Spark shell UI is available on port 4040.

In browser go to :   http://your_machine_address:4040
(use 'public' ip of machine)

Here is a sample screen shot:

<img src="../images/2a.png" style="border: 5px solid grey ; max-width:100%;" />

**==> Explore stage, storage, environment and executor tabs**

**==> Take note of 'Event Timeline', we will use this for monitoring our jobs later**

**==> Check spark master on port 8080,  Do you the Spark shell application connected?  Why (not)?**

---------------------
STEP 3: Spark context
---------------------
Within Spark  shell,  variable `sc` is the SparkContext
Type `sc` in scala prompt and see what happens.
Your output might look like this

```scala

    scala> sc
    res0: org.apache.spark.SparkContext = org.apache.spark.SparkContext@5019fb90
```


To see all methods in sc variable, type `sc.`  and `double-TAB`
This will show all the available methods on `sc` variable.
(This only works on Scala shell for now)

Try the following:

**==> Print the name of application name**
`sc.appName`

**==> Find the 'Spark master' for the shell**
`sc.master`


-------------------
STEP 4: Load a file
-------------------
We have data files under `spark-labs/data`
Use test file :  `data/twinkle/sample.txt` .
The file has a favorite nursery rhyme

    twinkle twinkle little star
    how I wonder what you are
    up above the world so high
    like a diamond in the sky
    twinkle twinkle little star

Let's load the file:

```scala 
    val f = sc.textFile("data/twinkle/sample.txt")
```


#### answer the following questions:

**==> What is the 'type' of f ?**   
hint : type `f` on the console

**==> Inspect Spark Shell UI on port 4040, do you see any processing done?  Why (not)?**

**==> Print the first line / record from RDD**  
hint : `f.first()`

**==> Again, inspect Spark Shell UI on port 4040, do you see any processing done?  Why (not)?**

**==> Print first 3 lines of RDD**  
hint : `f.take(???)`  (provide the correct argument to take function)

**==> Again, inspect Spark Shell UI on port 4040, do you see any processing done?  Why (not)?**

**==> Print all the content from the file**  
hint : `f.collect()`

**==> How many lines are in the file?**  
hint : `f.count()`

**==> Inspect the 'Jobs' section in Shell UI (in browser)**  
Also inspect the event time line

<img src="../images/2b.png" style="border: 5px solid grey; max-width:100%;" />


**==> Inspect the 'Executor' section in Shell UI (in browser)**

<img src="../images/2c.png" style="border: 5px solid grey; max-width:100%;" />

**==> Quit spark-shell session `Control + D`**


-------------------------------------------
STEP 5:  Connecting Shell and  Spark server
-------------------------------------------

**==> Quit spark-shell session, if you haven't done so yet.. `Control + D`**  

### 5.1 Start Spark Server
If Spark server is not running, start it as

```
    $ ~/spark/sbin/start-all.sh
```


Use `jps` command to inspect the java process.  Your output might look like this.
```console

    731 Master
    902 Jps
    831 Worker
```

Spark master UI is available on port 8080.
In browser go to :   http://your_machine_address:8080
(use 'public' ip of machine)

Here is a sample screen shot:

<img src="../images/2d.png" style="border: 5px solid grey; max-width:100%;" />

### 5.2 Now start spark shell

```
    $ ~/spark/bin/spark-shell
```



Once the shell starts, check the _server_ UI on port 8080.

**==> Do you see the shell connected as an application?  why (not) ?**

### Step 5.3 Connect Spark shell with the Spark server.
Make a note of Spark server uri (e.g  `spark://host_name:7077`)

**==> Restart spark shell as follows**

```
    $   ~/spark/bin/spark-shell   --master  spark-server-uri
                                            ^^^^^^^^^^^^^^^^
                                        update this to match your spark server

    $   ~/spark/bin/spark-shell   --master  spark://localhost:7077
```

On an Amazon server you may have to use the internal ip for the spark server, such as

```
    ~/spark/bin/spark-shell  --master spark://your_host_name:7077
```

On the ES VM you may have to use the localhost.localdomain. In all cases, follow what the spark master UI tells you.


**==> Once the shell started, check both UIs**

#### spark server UI at port 8080

<img src="../images/2e.png" style="border: 5px solid grey; max-width:100%;" />
---
#### spark shell UI at  port 4040

<img src="../images/2f.png" style="border: 5px solid grey; max-width:100%;" />


----------------------------------------
STEP 6: Redo step (4) in the new shell
----------------------------------------
Now our shell is connected to a server
**==> Load file and test it as in Step (4)**

-----------------------
Tip : Dealing With Logs
-----------------------
Spark Shell by default prints logs at warning (WARN) level.  If you want to change the logging level, do this at Spark shell
```
    sc.setLogLevel("INFO")
```

If you don't want to see any logs, you can start Spark shell as follows.  All the logs will be sent to 'logs' file.
```
    $    ~/spark/bin/spark-shell  2> logs
```
----------------------------------------
BONUS Lab 1 : Start multiple Shells
----------------------------------------
* Using one terminal, start a shell and connect to master  using **Step 5.3**
* Using second terminal (open one if you need to), start another shell connecting to the same master
* Check the master UI (port 8080).  You would see some thing like this, can you explain the behavior?

<img src="../images/2g.png" style="border: 5px solid grey ; max-width:100%;" />

----------------------------------------
TIPS and TRICKS: To run Linux command without leaving the shell
----------------------------------------

```bash
import sys.process._
val result = "ls -al"!
```
