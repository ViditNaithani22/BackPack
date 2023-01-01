## Get all products
curl.exe -i -X GET 'http://localhost:8080/products'

## Search products
curl.exe -i -X GET 'http://localhost:8080/products?name=bike'

## Get a product
curl.exe -i -X GET 'http://localhost:8080/products/3'

## Create a product
 curl.exe -i -X POST -H 'Content-Type:application/json' 'http://localhost:8080/products' -d '{\"name\": \"Redington Butter Stick w/ Tube Rod\",\"description\": \"The Redington Butter Stick w/ Tube Rod is a heritage inspired fiberglass fly rod.\",\"price\": 279.99,\"quantity\": 47}'

## Update a product
curl.exe -i -X PUT -H 'Content-Type:application/json' 'http://localhost:8080/products' -d '{\"id\": 1,\"name\": \"Decathlon Rockrider ST520 24-Speed Mountain Bike\",\"description\": \"Raised position., stem can be raised up to 1.18 in., ergonomic saddle: comfort that also yields greater efficiency.\",\"price\": 399.0,\"quantity\": 27}'

## Delete a product
curl.exe -i -X DELETE "http://localhost:8080/products/7"
