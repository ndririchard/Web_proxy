# Web_proxy
## About the project
The goal of this project is to set up a proxy server which allows on the one hand the encryption of the traffic and on the other hand assures an anonymity on Internet. To do this, we have developed two servers which by a socket communication make the data flow in the following way:

![image](https://user-images.githubusercontent.com/94222983/152293991-f6ab3990-4913-43fe-b77a-1d2baa0815c9.png)

## Installation 

### Get code source and run it 
```
git clone https://github.com/ndririchard/Web_proxy.git
cd Web_proxy
javac *.java
```
Then the java application is ready to run.

## Usage 
Once you have downloaded the project and create java classes, **you need to setup the proxy's host in the ProxyListener classe**.
Then setup the proxy server on your machine.<br>
On windows machines, you have to go in **setting> network&internet > proxy > Manual proxy**.
![image](https://user-images.githubusercontent.com/94222983/152889723-de82ebdd-2911-47b7-a08d-8073765cefe8.png)

After that you can run the proxy servers.
```
cd path/Web_proxy
java Run
```
