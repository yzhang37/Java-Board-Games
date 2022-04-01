work_path=$(dirname $(readlink -f $0))
cd ${work_path}

./requirements.sh
ret=$?
if [ $ret -ne 0 ]; then
  echo "Cannot find necessary libraries."
  exit $ret
fi

find . -name "*.java" > sources1.txt
grep -iv "test" sources1.txt > sources2.txt
rm sources1.txt
javac -classpath ./jna-5.10.0.jar @sources2.txt -d out/production/tictactoe

ret=$?
if [ $ret -ne 0 ]; then
  echo "Failed to build java."
  exit $ret
fi

rm sources2.txt
