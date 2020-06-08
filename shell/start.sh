#nohup java -jar mooc-class-service-1.0-SNAPSHOT.jar --spring.dubbo.protocol.port=20883 &
#nohup java -jar mooc-class-service-1.0-SNAPSHOT.jar --spring.dubbo.protocol.port=20882 &
nohup java -jar mooc-class-service-1.0-SNAPSHOT.jar --spring.dubbo.protocol.port=20881 &

#nohup java -jar mooc-class-web-1.0-SNAPSHOT.jar --spring.dubbo.protocol.port=10880 --server.port=8080 &
#nohup java -jar mooc-class-web-1.0-SNAPSHOT.jar --spring.dubbo.protocol.port=10881 --server.port=8081 &