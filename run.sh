./requirements.sh
ret=$?
if [ $ret -ne 0 ]; then
  echo "Cannot find necessary libraries."
  exit $ret
fi

java -classpath out/production/tictactoe:./jna-5.10.0.jar club.denkyoku.tictactoe.Main