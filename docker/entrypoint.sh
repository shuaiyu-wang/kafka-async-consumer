
if [  "$APP_ENV" ]; then
  profile="-Dspring.profiles.active=$APP_ENV"
fi

if [ ! "$JAVA_OPTS" ]; then
  JAVA_OPTS="-Xms256m -Xmx512m -Xss256k"
fi


java $JAVA_OPTS  $profile  -Djava.security.egd=file:/dev/./urandom  -jar /opt/ptyk/app.jar