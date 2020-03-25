curl --request POST -sL \
     --header 'Content-Type: application/json'\
     --url 'http://localhost:9001/api/v1/user'\
     --data '{
     "name":"name1",
     "age":1
     }'