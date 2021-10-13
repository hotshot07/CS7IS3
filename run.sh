echo 'Running Script'

mvn clean;
mvn package;
java -jar query/target/query-1.0-SNAPSHOT-shaded.jar;

for entry in "Results"/*
do
  echo "$entry"
  echo
  ./trec_eval/trec_eval -m map -m P.5,10 data/cran/QRelsCorrectedforTRECeval $entry
  printf "\n \n \n"
done