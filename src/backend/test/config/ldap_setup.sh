#!/bin/sh

DN="cn=Manager,dc=jamm,dc=test"
PW=jammtest

ldapdelete -r -x -D "$DN" -w $PW "dc=jamm,dc=test"
ldapadd -x -D "$DN" -w $PW -f setup.ldif
