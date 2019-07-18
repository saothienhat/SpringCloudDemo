
# Micro Services using Spring Cloud

Clone the repository with 
```
git clone https://github.com/saothienhat/SpringCloudDemo.git
```

## Project structure
```
--
	-- config-server : Config Server (Port: 8888)

```

## Config Service

- How to run Config Server project ?

```
cd configservice
mvn clean package

Run by commandline: java -jar target/configservice-0.0.1-SNAPSHOT.jar (starts on port 8888)
Run by Eclipse: Run As => Spring Boot App

Check on Brower / Postman [GET] : localhost:8888/assignment-about/default
Result:
	{
		"name": "assignment-about",
		"profiles": [
			"default"
		],
		"label": null,
		"version": "38a2dd7a1123c2c8517c4f5d16c49197ef448518",
		"state": null,
		"propertySources": [
			{
				"name": "http://52.231.66.29/binh.trinh/microservice-demo.git/configuration/assignment-about.properties",
				"source": {
					"text.copyright": "saothienhat@gmail.com"
				}
			}
		]
	}
```
