# ArcherLights

Dieses Projekt dient zur Steuerung einer Optischen- und Akkustischen-Anlage für das Bogenschießen.

Die Ampel-Anlage selbst wird über einen RaspberryPI angesteuert. Der RaspberryPI schlatet über die in diesem Projekt enthaltene Software ein Relais-Board, welche die 230V-Spannung für die Ampel regelt.

Für die Zentrale Kontroll-Einheit kann ein Laptop mit JAVA genutzt werden.
Die kommunikation der Komponenten untereinander geschieht über WLAN. Ein WLAN-Router ist daher vorraussetzung für die Anlage.

Projektübersicht:
* LIGHTS/<br />
  Enthält den nodejs-code für die Ampelsteuerung sowie die Ansible-Rolle zum einrichten des RaspberryPI
* CONTROLLER/<br />
  Enthält den JAVA-Code für die Steuerung
* docs/<br />
  Dokumente
