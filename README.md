Scala Thumbnailer
=================

Build
------
$ gradle distZip

Run
---
$ gradle run

Publishing:
-----------
$ gradle uploadArchives

### Requires a properties file in your .gradle folder
> signing.keyId=
> signing.password=
> signing.secretKeyRingFile=
>
> sonatypeUsername=
> sonatypePassword=
