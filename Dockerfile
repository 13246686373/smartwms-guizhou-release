FROM openjdk:8-jre-alpine
VOLUME /tmp
ADD build/libs/wms-1.0.0.jar wms.jar
CMD ["java", "-Denv=dev", "-Dapp.id=smartscm", "-Xmx200m", "-jar", "/wms.jar"]
EXPOSE 9090