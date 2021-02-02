#Request A GET
import requests
response = requests.get("http://localhost:8080/jdbc-products/1")
request_1 = response.json()
print ("User A:", request_1)