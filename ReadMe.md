## README
## TMA Application install guide

Command handler is dummy project to logistic handle

## Integration continue

### Membre du groupe : 
   - Delage Corentin
   - Meire Valentin
   - Dekyndt Florentin

Afin de respecter la configuration de mon Jenkinsfile, il est nécessaire de respecter la configuration ci-dessous.

Pré-requis: 
- Télécharger le .war Jenkins
- Télécharger l'executable Nexus
- Installer le plugin Nexus plateform dans votre Jenkins
### Configuration serveur Nexus :

Dans le fichier "settings.xml" de votre dossier Maven

    <servers>
        ...
        <server>
          <id>nexusLocal</id>
          <username>admin</username>
          <password>admin</password>
        </server>
        ...
     </servers>
     
Cela permet d'automatiser le transfert de l'artifact généré par Maven pour l'envoyer dans votre dépôt Nexus.

Il faut faire de même au sein de votre Jenkins:

Dans l'administration Jenkins:

- Display Name : Nexus_localhost

- Server ID : Nexus_localhost

- Server URL : http://localhost:8081
