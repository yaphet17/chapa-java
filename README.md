```
 ,-----. ,--.                                             ,--.                              
'  .--./ |  ,---.   ,--,--.  ,---.   ,--,--. ,-----.      |  |  ,--,--. ,--.  ,--.  ,--,--. 
|  |     |  .-.  | ' ,-.  | | .-. | ' ,-.  | '-----' ,--. |  | ' ,-.  |  \  `'  /  ' ,-.  | 
'  '--'\ |  | |  | \ '-'  | | '-' ' \ '-'  |         |  '-'  / \ '-'  |   \    /   \ '-'  | 
 `-----' `--' `--'  `--`--' |  |-'   `--`--'          `-----'   `--`--'    `--'     `--`--'
```

[![BUILD](https://github.com/yaphet17/chapa-java/actions/workflows/maven.yml/badge.svg)](https://github.com/yaphet17/chapa-java/actions/workflows/maven.yml/) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.yaphet17/Chapa/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.yaphet17/Chapa) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) 

Unofficial Java SDK for Chapa Payment Gateway.

## What's new in this version
- You no longer need to deal with `JSON` or `Map<String, Object>` responses. You can just treat response data as a Java object using specific response classes for each request type (e.g. payment initialization, payment verification).
- Better exception handling. The SDK will throw the newly added `ChapaException` on failed requests to Chapa API.
- Bug fixes and design improvements.
- Well-tested and documented code. Check out the Javadoc [here](https://yaphet17.github.io/chapa-java/). 

## Table of Contents
1. [Documentation](#documentation)
2. [Installation](#installation)
3. [Usage](#usage)
4. [Javadoc](https://yaphet17.github.io/chapa-java/)
5. [Contribution](#contribution)
6. [Example](#example)
7. [License](#license)

## Documentation
Visit official [Chapa's API Documentation](https://developer.chapa.co/docs)
## Installation
 Add the below maven dependency to your `pom.xml` file.
```xml
    <dependency>
      <groupId>io.github.yaphet17</groupId>
      <artifactId>Chapa</artifactId>
      <version>1.2.2</version>
    </dependency>
```
Or add the below gradle dependency to your `build.gradle` file.
```groovy
    implementation 'io.github.yaphet17:Chapa:1.2.2'
```

## Usage

Instantiate a `Chapa` class.
```java       
Chapa chapa = new Chapa("your-secrete-key");
```
Or if you want to use your own implementation of `ChapaClient` interface.
```java
Chapa chapa = new Chapa("your-secrete-key", new MyCustomChapaClient());
```
Note: `MyCustomChapaClient` must implement `ChapaClient` interface.

To initialize a transaction, you can specify your information by either using our `PostData` class.

Note: Starting from version 1.1.0 you have to specify customization fields as a `Map<String, String>` object.

```java
Customization customization = new Customization()
    .setTitle("E-commerce")
    .setDescription("It is time to pay")
    .setLogo("https://mylogo.com/log.png");
PostData postData = new PostData()
    .setAmount(new BigDecimal("100"))
    .setCurrency("ETB")
    .setFirstName("Abebe")
    .setLastName("Bikila")
    .setEmail("abebe@bikila")
    .setTxRef(Util.generateToken())
    .setCallbackUrl("https://chapa.co")
    .setReturnUrl("https://chapa.co")
    .setSubAccountId("testSubAccountId")
    .setCustomization(customization);
```
Or, you can use a string JSON data.
```java 
String formData = " { " +
        "'amount': '100', " +
        "'currency': 'ETB'," +
        "'email': 'abebe@bikila.com'," +
        "'first_name': 'Abebe'," +
        "'last_name': 'Bikila'," +
        "'tx_ref': 'tx-myecommerce12345'," +
        "'callback_url': 'https://chapa.co'," +
        "'subaccount[id]': 'ACCT_xxxxxxxxx'," +
        "'customizations':{" +
        "       'customization[title]':'E-commerce'," +
        "       'customization[description]':'It is time to pay'," +
        "       'customization[logo]':'https://mylogo.com/log.png'" +
        "   }" +
        " }";
```
Initialize payment
```java
InitializeResponseData responseData = chapa.initialize(postData);
// Get the response message
System.out.println(responseData.getMessage());
// Get the checkout URL from the response JSON
System.out.println(responseData.getData().getCheckOutUrl());
// Get the raw response JSON
System.out.println(responseData.getRawJson());
```
Verify payment
```java
// Get the verification response data
VerifyResponseData verifyResponseData = chapa.verify("tx-myecommerce12345");
```
Get the list of banks
```java
List<Bank> banks = chapa.getBanks();
```
To create a subaccount, you can specify your information by either using our `Subaccount` class.
```java
SubAccount subAccount = new SubAccount()
                .setBusinessName("Abebe Suq")
                .setAccountName("Abebe Bikila")
                .setAccountNumber("0123456789")
                .setBankCode("96e41186-29ba-4e30-b013-2ca36d7e7025")
                .setSplitType(SplitType.PERCENTAGE)
                .setSplitValue(0.2);
```
Or, you can use a string JSON data.
```java
String subAccount = " { " +
        "'business_name': 'Abebe Suq', " +
        "'account_name': 'Abebe Bikila'," +
        "'account_number': '0123456789'," +
        "'bank_code': '96e41186-29ba-4e30-b013-2ca36d7e7025'," +
        "'split_type': 'percentage'," +
        "'split_value': '0.2'" +
        " }";
```
Create subaccount
```java
SubAccountResponseData subAccountResponseData = chapa.createSubAccount(subAccount);
// Get SubAccount id from the response JSOn
System.out.println(subAccountResponseData.getData().getSubAccountId());
```
## Example
```java
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.yaphet17.chapa.Chapa;
import io.github.yaphet17.chapa.PostData;
import io.github.yaphet17.chapa.SubAccount;
import io.github.yaphet17.chapa.SplitType;
import io.github.yaphet17.chapa.Bank;

public class ChapaExample {

    public static void main(String[] args) {
      Chapa chapa = new Chapa("your-secrete-key");
    
      Customization customization = new Customization()
                .setTitle("E-commerce")
                .setDescription("It is time to pay")
                .setLogo("https://mylogo.com/log.png");

      PostData postData = new PostData()
                .setAmount(new BigDecimal("100"))
                .setCurrency("ETB")
                .setFirstName("Abebe")
                .setLastName("Bikila")
                .setEmail("abebe@bikila")
                .setTxRef(Util.generateToken())
                .setCallbackUrl("https://chapa.co")
                .setReturnUrl("https://chapa.co")
                .setSubAccountId("testSubAccountId")
                .setCustomization(customization);
      
      SubAccount subAccount = new SubAccount()
                .setBusinessName("Abebe Suq")
                .setAccountName("Abebe Bikila")
                .setAccountNumber("0123456789")
                .setBankCode("96e41186-29ba-4e30-b013-2ca36d7e7025")
                .setSplitType(SplitType.PERCENTAGE)
                .setSplitValue(0.2);

       
       InitializeResponseData responseData = chapa.initialize(postData);
       VerifyResponseData verifyResponseData = chapa.verify("tx-myecommerce12345");
       SubAccountResponseData subAccountResponseData = chapa.createSubAccount(subAccount);

      }
 }
```
## Contribution
If you find any bugs or have any suggestions, please feel free to open an issue or pull request.

## License
This open-source library is licensed under the terms of the MIT License.

Enjoy!
