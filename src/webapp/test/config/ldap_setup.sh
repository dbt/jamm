#!/bin/sh

DN="cn=Manager,dc=jamm,dc=local"
PW=jammtest

ldapdelete -r -x -D "$DN" -w $PW "dc=jamm,dc=local"
ldapadd -x -D "$DN" -w $PW -f setup.ldif
ldappasswd -x -D "$DN" -w $PW -s mgr1pw \
	"cn=Manager, ou=domain1.net, ou=email, dc=jamm, dc=local"
ldappasswd -x -D "$DN" -w $PW -s mgr2pw \
	"cn=Manager, ou=domain2.com, ou=email, dc=jamm, dc=local"
ldappasswd -x -D "$DN" -w $PW -s acct1pw \
	"mail=acct1@domain1.net, ou=domain1.net, ou=email, dc=jamm, dc=local"
ldappasswd -x -D "$DN" -w $PW -s acct3pw \
	"mail=acct3@domain2.com, ou=domain2.com, ou=email, dc=jamm, dc=local"