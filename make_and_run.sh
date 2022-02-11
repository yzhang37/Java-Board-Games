work_path=$(dirname $(readlink -f $0))
cd ${work_path}

echo "Building..."
javac src/club/denkyoku/TicTacToe/*.java -d out/production/Tic-Tac-Toe
ret=$?
if [ $ret -ne 0 ]; then
  echo "Failed to build java."
  exit $ret
fi

(cd out/production/Tic-Tac-Toe/ && java club.denkyoku.TicTacToe.Main)
