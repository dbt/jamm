#!/usr/bin/perl
#
# Upgrades an ldif from Jamm pre-0.9.4 to 0.9.4 or later

use Net::LDAP::LDIF;
use Set::Scalar;

sub getSet
{
    my $set = Set::Scalar->new;
    my @array = @_;
    while (@array)
    {
        $name = pop(@array);
        $name =~ tr/A-Z/a-z/;
        $set->insert($name);
    }
    return $set;
}

$ARGC = @ARGV;
if ($ARGC < 2)
{
    print "Usage: updateLdif.pl <old_file> <new_file>\n";
    exit(1);
}

$oldldif_filename = $ARGV[0];
$tmpfile = $oldldif_filename . "." . $$;
$newldif_filename = $ARGV[1];

# This comments out the known bad lines in the original file.
open(INPUT, $oldldif_filename) || die ("Can't open old file: $!");
open(OUTPUT, ">$tmpfile") || die ("Can't open tmpfile: $!");
while (<INPUT>)
{
    if (/^version:/ || /^search:/ || /^result:/)
    {
        print OUTPUT "#";
    }
    print OUTPUT $_;
}
close INPUT;
close OUTPUT;

$oldldif = Net::LDAP::LDIF->new($tmpfile, "r", onerror => "warn",
                                encode => "base64");
$newldif = Net::LDAP::LDIF->new($newldif_filename, "w", onerror => "warn",
                                encode => "base64");

while (not $oldldif->eof())
{
    my $entry = $oldldif->read_entry();
    if (!$entry)
    {
        next;
    }
    if ($oldldif->error())
    {
        print "Error msg: ", $oldldif->error(),"\n";
        print "Error lines:\n",$oldldif->error_lines(),"\n";
    }
    else
    {
        $objectSet = getSet($entry->get_value("objectClass"));
        if ($objectSet->has("organizationalrole"))
        {
            if (!($entry->get_value(cn) eq "Manager"))
            {
                $entry->replace("objectClass" =>
                                [qw(JammMailAlias JammPostmaster)]
                               );
            }
        }
        if ($objectSet->has("jammvirtualdomain"))
        {
            my $jvd = $entry->get_value("jvd");
            print "Updating entry jvd=$jvd\n";
            $entry->delete("editCatchAlls");
            $entry->delete("editAliases");
            if (!$entry->exists("delete"))
            {
                $entry->add("delete" => "FALSE");
            }
            $entry->replace("accountActive" => "TRUE");
            $entry->replace("lastChange" => time());
        }

        if ($objectSet->has("jammmailaccount"))
        {
            my $account = $entry->get_value("mail");
            print "Updating account entry mail=$account\n";
            if (!$entry->exists("delete"))
            {
                $entry->add("delete" => "FALSE");
            }
            $entry->replace("accountActive" => "TRUE");
            $entry->replace("lastChange" => time());
        }

        if ($objectSet->has("jammmailalias"))
        {
            my $alias = $entry->get_value("mail");
            print "Updating alias entry mail=$alias\n";
            $entry->replace("lastChange" => time());
            $entry->replace("accountActive" => "TRUE");
        }

        $objectSet->clear();
        $newldif->write_entry($entry);
    }
}

$oldldif->done();
$newldif->done();

unlink $tmpfile;
