// Lien vers Nexus, doit correspondre à l'instance paramétrée dans Jenkins
def nexusId = 'Nexus_localhost'

/* *** Configuration de Nexus pour Maven ***/
// URL de Nexus
def nexusUrl = 'http://localhost:8081'
// Repo Id (provient du settings.xml nexus pour récupérer user/password)
def mavenRepoId = 'nexusLocal'

/* *** Repositories Nexus *** */
def nexusRepoSnapshot = "maven-snapshots" //contient les versions de l'application qui évolue mais toujours en cours de développement (branche develop)
def nexusRepoRelease = "maven-releases" //contient les versions de l'application destinées à être déployé (branche release)



/* *** Détail du projet, récupéré dans le pipeline en lisant le pom.xml *** */
def groupId = ''
def artefactId = ''
def filePath = ''
def packaging = ''
def version = ''

// Variable utilisée pour savoir si c'est une RELEASE ou une SNAPSHOT
def isSnapshot = true

pipeline {
    agent any

    stages {
        stage('Checkout') {
           steps {
              checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/Florentin-Dt/Order_Handler.git']]])
           }
        }
        stage('Get info from POM') {
            steps {
                script {
                    pom = readMavenPom file: 'pom.xml'
                    groupId = pom.groupId
                    artifactId = pom.artifactId
                    packaging = pom.packaging
                    version = pom.version
                    filepath = "target/${artifactId}-${version}.jar"
                    isSnapshot = version.endsWith("-SNAPSHOT")
                }
                echo groupId
                echo artifactId
                echo packaging
                echo version
                echo filepath
                echo "isSnapshot: ${isSnapshot}"
            }
          }
        stage('Build') {
            steps {
               echo 'Build...'
               sh 'mvn clean'
               sh 'mvn compile'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                echo 'Packaging...'
                sh 'mvn package'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
        stage('Analyse') {
            steps {
                echo 'Analyse...'
                sh 'mvn site'
                sh 'mvn checkstyle:checkstyle'
                sh 'mvn spotbugs:spotbugs'
                sh 'mvn pmd:pmd'
            }
        }
        stage('Push SNAPSHOT to Nexus') {
            when { expression { isSnapshot } }
            steps {
              sh "mvn deploy:deploy-file -e -DgroupId=${groupId} -Dversion=${version} -Dpackaging=${packaging} -Durl=${nexusUrl}/repository/${nexusRepoSnapshot} -Dfile=${filepath} -DartifactId=${artifactId} -DrepositoryId=${mavenRepoId}"
            }
        }
        stage('Push RELEASE to Nexus') {
            when { expression { !isSnapshot } }
            steps {
                nexusPublisher nexusInstanceId: 'Nexus_localhost', nexusRepositoryId: "${nexusRepoRelease}", packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: "${filepath}"]], mavenCoordinate: [artifactId: "${artifactId}", groupId: "${groupId}", packaging: "${packaging}", version: "${version}"]]]
            }
        }
    }
    post {
        always {
            junit '**/surefire-reports/*.xml'
            recordIssues enabledForFailure: true, tools: [mavenConsole(), java(), javaDoc()]
            recordIssues enabledForFailure: true, tool: checkStyle()
            recordIssues enabledForFailure: true, tool: spotBugs()
            recordIssues enabledForFailure: true, tool: cpd(pattern: '**/target/cpd.xml')
            recordIssues enabledForFailure: true, tool: pmdParser(pattern: '**/target/pmd.xml')
        }
    }
}