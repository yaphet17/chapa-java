```
 ,-----. ,--.                                             ,--.                              
'  .--./ |  ,---.   ,--,--.  ,---.   ,--,--. ,-----.      |  |  ,--,--. ,--.  ,--.  ,--,--. 
|  |     |  .-.  | ' ,-.  | | .-. | ' ,-.  | '-----' ,--. |  | ' ,-.  |  \  `'  /  ' ,-.  | 
'  '--'\ |  | |  | \ '-'  | | '-' ' \ '-'  |         |  '-'  / \ '-'  |   \    /   \ '-'  | 
 `-----' `--' `--'  `--`--' |  |-'   `--`--'          `-----'   `--`--'    `--'     `--`--'
```

[![BUILD](https://github.com/yaphet17/chapa-java/actions/workflows/maven.yml/badge.svg)](https://github.com/yaphet17/chapa-java/actions/workflows/maven.yml/) [![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/yaphet17/chapa-java.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/yaphet17/chapa-java/context:java) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) 

Unofficial Java package for Chapa Payment Gateway.

## What's new in this version
- You can now implement `ChapaClient` interface and create your own custom implementation
  to use your favorite HTTP client.
- Includes split payment feature added by Chapa. You can now get list of supported banks, create
  subaccount and perform a split payment. See [Split Payment](https://developer.chapa.co/docs/split-payment/) documentation for more details.
- Additional utility methods to help you to generate a convenient token for your transactions, to map json string
  to `PostData` object etc.
- Bug fixes and design improvements.
- Well tested and documented code.

## Table of Contents
1. [Documentation](#documentation)
2. [Installation](#installation)
3. [Usage](#usage)
4. [Contribution](#contribution)
5. [Example](#example)
6. [License](#license)

## Documentation
Visit official [Chapa's API Documentation](https://developer.chapa.co/docs)
## Installation
 Add the below maven dependency to your `pom.xml` file.
```xml
    <dependency>
      <groupId>io.github.yaphet17</groupId>
      <artifactId>Chapa</artifactId>
      <version>1.1.0</version>
    </dependency>
```
Or add the below gradle dependency to your `build.gradle` file.
```groovy
    implementation 'io.github.yaphet17:Chapa:1.1.0'
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

To initialize transaction, you can specify your information by either using our `PostData` class.

Note: Starting from version 1.1.0 you have to specify customization fields as a `Map<String, String>` object.

```java
Map<String, String> customizations = new HashMap<>();
customizations.put("customization[title]", "E-commerce");
customizations.put("customization[description]", "It is time to pay");
customizations.put("customization[logo]", "https://mylogo.com/log.png");
PostData postData = PostData.builder()
        .amount(new BigDecimal("100"))
        .currency("ETB")
        .firstName("Abebe")
        .lastName("Bikila")
        .email("abebe@bikila.com")
        .txRef(Util.generateToken())
        .callbackUrl("https://chapa.co")
        .subAccountId("ACCT_xxxxxxxxx")
        .customizations(customizations)
        .build();
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
Intitialize payment
```java
String reponseString = chapa.initialize(formData).asString(); // get response in a string JSON format
Map<String, String> responseMap = chapa.initialize(formData).asMap(); // get response as a Map object 
```
Verify payment
```java
String reponseString = chapa.verify("tx-myecommerce12345").asString(); // get response in a string JSON format
Map<String, String> responseMap = chapa.verify("tx-myecommerce12345").asMap(); // get response as a Map object 
```
Get list of banks
```java
List<Bank> banks = chapa.getBanks();
```
To create a subaccount, you can specify your information by either using our `Subaccount` class.
```java
SubAccount subAccount = SubAccount.builder()
        .businessName("Abebe Suq")
        .accountName("Abebe Bikila")
        .accountNumber("0123456789")
        .bankCode("96e41186-29ba-4e30-b013-2ca36d7e7025")
        .splitType(SplitType.PERCENTAGE) // or SplitType.FLAT
        .splitValue(0.2)
        .build();
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
String reponseString = chapa.createSubAccount(subAccount).asString(); // get response in a string JSON format
Map<String, String> responseMap = chapa.createSubAccount(subAccount).asMap(); // get response as a Map object 
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
    
      Map<String, String> customizations = new HashMap<>();
      customizations.put("customization[title]", "E-commerce");
      customizations.put("customization[description]", "It is time to pay");
      customizations.put("customization[logo]", "https://mylogo.com/log.png");
      PostData postData = PostData.builder()
              .amount(new BigDecimal("100"))
              .currency("ETB")
              .firstName("Abebe")
              .lastName("Bikila")
              .email("abebe@bikila.com")
              .txRef(Util.generateToken())
              .callbackUrl("https://chapa.co")
              .subAccountId("ACCT_xxxxxxxxx")
              .customizations(customizations)
              .build();
      
      SubAccount subAccount = SubAccount.builder()
              .businessName("Abebe Suq")
              .accountName("Abebe Bikila")
              .accountNumber("0123456789")
              .bankCode("96e41186-29ba-4e30-b013-2ca36d7e7025")
              .splitType(SplitType.PERCENTAGE) // or SplitType.FLAT
              .splitValue(0.2)
              .build();

      // list of banks
      List<Bank> banks = chapa.banks();
      banks.forEach(bank -> System.out.println("Bank name: " + bank.getName() + " Bank Code: " + bank.getId()));
      // create subaccount
      System.out.println("Create SubAccount response: " + chapa.createSubAccount(subAccount).asString());
      // initialize payment
      System.out.println("Initialize response: " + chapa.initialize(postData).asString());
      // verify payment
      System.out.println("Verify response: " + chapa.verify(postData.getTxRef()).asString());
```
## Contribution
If you find any bug or have any suggestion, please feel free to open an issue or pull request.

## License
This open source library is licensed under the terms of the MIT License.

Enjoy!