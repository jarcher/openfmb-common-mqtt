![Logo of the Project](https://github.com/openfmb/dtech-demo-2016/blob/master/img/openfmb-tm-black_reduced_100.png)

# Description

This repository is the OpenFMB interface layer that creates java objects for producing the required XML for the MQTT Transport.  Additionally this layer provides the applicaiton interfaces  

# Installing / Getting started

{starting notes} NOTE: This project is used in the Dtech Demo.  Refer to [Wiki](https://github.com/openfmb/dtech-demo-2016/wiki) for information on DTech Demo. 

```shell
java -cp openfmb-loadpublisher-1.0-jar-with-dependencies.jar com.dukeenergy.eto.openfmb.LoadPublisher
```

## Building

```shell
git clone https://github.com/openfmb/openfmb-common-mqtt.git
cd openfmb-common-mqtt/openfmb-core
mvn clean install
```
The build jar is put in the target directory and needs to be moved to the main directory where the properities files are located. 


## Configuration

There is no configuration for this repository.

# Contributing

David Lawrence, Duke Energy

Dwayne Bradley, Duke Energy

If you'd like to contribute, please fork the repository and use a feature
branch. Pull requests are warmly welcome.

Please review the [CONTRIBUTING](https://github.com/openfmb/openfmb-common-mqtt/blob/master/CONTRIBUTING.md) file. 

# License

See the [APACHE_FILE_HEADER](https://github.com/openfmb/openfmb-common-mqtt/blob/master/APACHE_FILE_HEADER) file for more info.
