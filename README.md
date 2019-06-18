# epsi-ci-survey

[![Build Status](https://travis-ci.com/nicolas59/epsi-ci-survey.svg?branch=master)](https://travis-ci.com/nicolas59/epsi-ci-survey)


[JWT]
* generation de la clé privée avec certificat 
```
openssl req -newkey rsa:2048 -new -nodes -x509 -days 3650 -keyout key.pem -out cert.pem
```

* generation de la clé publique
```
openssl rsa -in key.pem -pubout -out public-key.pem
```
 