# Acme Cache

This is an example of an independent cache implementation to help explain the
[jsr 107 tck](https://github.com/jsr107/jsr107tck).

Acme cache passes tests in CacheManagerFactoryTest

    mvn -Dtest=CacheManagerFactoryTest \
        -Dimplementation-groupId=acme.cache \
        -Dimplementation-artifactId=acme-cache \
        -Dimplementation-version=0.1-SNAPSHOT \
        test
