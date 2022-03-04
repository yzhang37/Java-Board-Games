path1="jna-5.10.0.jar"
if [ ! -f $path1 ]
then
  curl "https://repo1.maven.org/maven2/net/java/dev/jna/jna/5.10.0/$path1" -o $path1;
  ret=$?
  exit $ret
fi
exit 0