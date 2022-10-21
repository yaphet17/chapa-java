# Chapa-Java

[![BUILD](https://github.com/yaphet17/chapa-java/actions/workflows/maven.yml/badge.svg)](https://github.com/yaphet17/chapa-java/actions/workflows/maven.yml/) [![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/yaphet17/chapa-java.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/yaphet17/chapa-java/context:java) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) 

Unofficial Java package for Chapa Payment Gateway.
## Documentation
Visit official [Chapa's API Documentation](https://developer.chapa.co/docs)
## Installation
 Add the below maven dependency to your `pom.xml` file.
```xml
    <dependency>
      <groupId>io.github.yaphet17</groupId>
      <artifactId>Chapa</artifactId>
      <version>1.0.0</version>
    </dependency>
```
Or add the below gradle dependency to your `build.gradle` file.
```groovy
    implementation 'io.github.yaphet17:Chapa:1.0.0'
```

## Usage

Instantiate a `Chapa` class.
```java       
Chapa chapa = new Chapa("you-secrete-key");
```
To initialize transaction, you can specify your information by either using our `PostData` class

```java
Map<String, String> customizations = new HashMap<>();
customizations.put("customization[title]", "E-commerce");
customizations.put("customization[description]", "It is time to pay");
customizations.put("customization[logo]", "https://mylogo.com/log.png");
PostData formData = PostData.builder()
        .amount(new BigDecimal("100"))
        .currency( "ETB")
        .first_name("Abebe")
        .last_name("Bikila")
        .email("abebe@bikila.com")
        .tx_ref("tx-myecommerce12345")
        .callback_url("https://chapa.co")
        .customization(customizations)
        .build();
```
Or, you can use a string JSON data
```java
 String formData = " {
    'amount': '100',
    'currency': 'ETB',
    'email': 'abebe@bikila.com',
    'first_name': 'Abebe',
    'last_name': 'Bikila',
    'tx_ref': 'tx-myecommerce12345',
    'callback_url': 'https://chapa.co',
    'customization[title]': 'I love e-commerce',
    'customization[description]': 'It is time to pay'
  }"
}
```
Intitialize payment
```java
String reponseString = chapa.initialize(formData).asString(); // get reponse in a string JSON format
Map<String, String> responseMap = chapa.initialize(formData).asMap(); // get reponse as a Map object 
```
Verify payment
```java
String reponseString = chapa.verify("tx-myecommerce12345").asString(); // get reponse in a string JSON format
Map<String, String> responseMap = chapa.verify("tx-myecommerce12345").asMap(); // get reponse as a Map object 
```
