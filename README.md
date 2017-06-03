## Synopsis

This application provides a REST interface to create or view offers for a merchant. It accepts requests in `json` format and returns success or failure reposne.

It provides two functionalities:

- Create an offer (via http POST request)
- View all offers (via http GET request)

## Code Examples

1. To create an offer, application accepts the inputs in following form:

```javascript
{
	"name" : "Toothpaste offer",
	"description" : "Toothpaste and Toothbrush combo super saver offer",
	"currency": "GBP",
	"price": 10,
	"products" : [
		{
			"name" : "Toothpaste",
			"company" : "Signal"
		},
		{
			"name" : "Tooth brush",
			"company" : "Signal"
		}
	]
}
```

And produces the following output:

```javascript
{
  "id": 1,
  "name": "Toothpaste Offer",
  "description": "Toothpaste and Toothbrush combo super saver offer",
  "price": 10,
  "currency": "GBP",
  "products": [
    {
      "id": 1,
      "name": "Toothpaste",
      "company": "Signal"
    },
    {
      "id": 2,
      "name": "Toothbrush",
      "company": "Signal"
    }
  ]
}
```

`id` field is the generated id for Offer and Product

All the fields in create offer request are mandatory (except id). If fields are not provided, the API returns 400 with appropriate messages. Below is an example request:

```javascript
{
    "name": "Toothpaste Offer"
}
```

This produces the following output:

```javascript
{
  "errors": [
    {
      "codes": [
        "NotNull.offer.description",
        "NotNull.description",
        "NotNull.java.lang.String",
        "NotNull"
      ],
      "arguments": [
        {
          "codes": [
            "offer.description",
            "description"
          ],
          "arguments": null,
          "defaultMessage": "description",
          "code": "description"
        }
      ],
      "defaultMessage": "Offer description is required",
      "objectName": "offer",
      "field": "description",
      "rejectedValue": null,
      "bindingFailure": false,
      "code": "NotNull"
    },
    {
      "codes": [
        "NotEmpty.offer.products",
        "NotEmpty.products",
        "NotEmpty.java.util.List",
        "NotEmpty"
      ],
      "arguments": [
        {
          "codes": [
            "offer.products",
            "products"
          ],
          "arguments": null,
          "defaultMessage": "products",
          "code": "products"
        }
      ],
      "defaultMessage": "Offer products is required",
      "objectName": "offer",
      "field": "products",
      "rejectedValue": null,
      "bindingFailure": false,
      "code": "NotEmpty"
    },
    {
      "codes": [
        "NotNull.offer.price",
        "NotNull.price",
        "NotNull.java.lang.Double",
        "NotNull"
      ],
      "arguments": [
        {
          "codes": [
            "offer.price",
            "price"
          ],
          "arguments": null,
          "defaultMessage": "price",
          "code": "price"
        }
      ],
      "defaultMessage": "Offer price is required",
      "objectName": "offer",
      "field": "price",
      "rejectedValue": null,
      "bindingFailure": false,
      "code": "NotNull"
    },
    {
      "codes": [
        "NotNull.offer.currency",
        "NotNull.currency",
        "NotNull.java.lang.String",
        "NotNull"
      ],
      "arguments": [
        {
          "codes": [
            "offer.currency",
            "currency"
          ],
          "arguments": null,
          "defaultMessage": "currency",
          "code": "currency"
        }
      ],
      "defaultMessage": "Offer currency is required",
      "objectName": "offer",
      "field": "currency",
      "rejectedValue": null,
      "bindingFailure": false,
      "code": "NotNull"
    }
  ]
}
```

2. To View all the offers, applications accpts GET request, e.g.:

```javascript
curl -X GET "http://localhost:8585/offers"
```

`GET` returns paged response and supports parameters for configuring page, size and sort. E.g. Below request returns last 5 offers by id

```javascript
curl -X GET "http://localhost:8585/offers?sort=id,desc&page=0&size=5"
```

## Installation

To install the app, run through the following steps:

* Clone the repository
* Make sure the machine has [Java](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html) and [Maven](https://maven.apache.org/download.cgi) installed.
* From the command line, type `mvn clean install`, this will start the installation process
* Once installation is complete, the `jar` file will be present inside `target` folder. This file is an executable file and can be run via `java -jar <file>.jar` command
* By default, the application starts on 8585 port and has `/` configured as context path. However, these parameters can be changed via application.properties file
* Application uses file based h2 database to store the data, defaulting to home directory of user. However, this can be changed by altering `spring.datasource.url` value in application.properties file 

Below is the example curl request to test the app once it's up and running:

```
curl -X POST \
  http://localhost:8585/offers \
  -d '{
	"name" : "Toothpaste Offer",
	"description" : "Toothpaste and Toothbrush combo",
	"currency": "GBP",
	"price": 10,
	"products" : [
		{
			"name" : "Toothpaste",
			"company" : "Signal"
		},
		{
			"name" : "Tooth brush",
			"company" : "Signal"
		}
	]
}'
```

```
curl -X GET http://localhost:8585/offers
```

## Tests

This application has built in tests. While building the app with `mvn clean install` command, these tests are exectuted and result is displayed in the console.
