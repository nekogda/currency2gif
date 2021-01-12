# Currency2Gif

Сервис для конвертации валюты в настроение (gif).

### Install and run tests

#### Download sources
```
$ git clone https://github.com/nekogda/currency2gif
$ cd currency2gif
```

#### Run tests
```
$ ./gradlew :test
```
#### Run service
```
$ ./gradlew :bootRun
```
#### Usage
Запустить браузер и перейти по адресу http://localhost:8080/

#### Configuration

Сервис запускается с настройками по умолчанию (`application.yaml` в ресурсах)

```yaml
currency2gif:
  
  gifServiceApiUrl: https://api.giphy.com/v1
  gifServiceAppId: "api_key"
  gifServiceRating: r
  gifServiceConnectionTimeoutSec: 5
  gifServiceReadTimeoutSec: 15
  
  exchangeRateServiceApiUrl: https://openexchangerates.org/api
  exchangeRateServiceAppId: "api_key"
  exchangeRateServiceConnectionTimeoutSec: 5
  exchangeRateServiceReadTimeoutSec: 15
  
  exchangeRateServiceBaseCode: USD
  exchangeRateServiceCurrencyCode: RUB
```

Чтобы изменить настройки по умолчанию - создайте файл application.yml в текущей директории
и наполните его в соответствии с примером выше. 
Почитать более подробно о том, как можно передать настройки
в приложение можно по [ссылке](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config-files).
