
# Installation

## Den RaspberryPI vorbereiten

Dieses Setup ist für die RaspberryPI Distribution "Raspbian" entwickelt worden.
Raspbian kann auf der Seite von RaspberryPI heruntergeladen werden: https://downloads.raspberrypi.org/raspbian_lite_latest

Das heruntergeladene ZIP muss Anschließend entpackt und auf eine MicroSD-Karte
kopiert werden.

Details, wie man Raspbian auf die SD-Karte bekommt finden man unter https://www.raspberrypi.org/documentation/installation/installing-images/

## Installation der ArcherLights-Software

Die einfachste Art die Client-Software auf den RaspberryPI zu bringen,
ist das setup.sh-Skript. Dies kann direkt auf dem PI ausgeführt werden. Alle
erforderlichen Komponenten werden heruntergeladen und auf dem PI installiert.

`curl -sL https://raw.githubusercontent.com/arogarth/ArcherLights/master/LIGHTS/setup.sh | sudo bash -`

Das setup.sh-Skript installiert die Pakete Ansible und Git auf dem RaspberryPI
und klont dann das ArcheryLights-Projekt auf den PI. Anschließend wir die Setup-Routine
mittels Ansible ausgeführt.

* GPIO17 - REL8 - RED
* GPIO27 - REL7 - YELLOW
* GPIO22 - REL6 - GREEN
* GPIO23 - REL5 - AB
* GPIO24 - REL4 - CD
* GPIO25 - REL3 - HORN
* GPIO04 - REL2 - ...
* GPIO18 - REL1 - ...
