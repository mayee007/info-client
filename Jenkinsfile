pipeline {
    agent { 
		label 'dockerserver'
	}

    parameters { 
        string(name: 'mavenGroupId', defaultValue: 'com.mine', description: '')
        string(name: 'mavenArtifactId', defaultValue: 'info-client', description: '')
        string(name: 'artifactName', defaultValue: 'info-client.war', description: '')
        string(name: 'version', defaultValue: '1.1.0', description: '')
        string(name: 'packaging', defaultValue: 'war', description: '')
        string(name: 'destination', defaultValue: '/root/info-scripts/info-client', description: '')
        string(name: 'artifactRepositoryUrl', defaultValue: 'http://localhost:8081/artifactory/snapshots', description: '')
        
    }

    stages {
        stage('Preparation') {
            
            steps {
	       // github repository 
               git 'https://github.com/mayee007/info-client.git'
            }
        }
        stage('Build') {
            steps {
               sh 'mvn clean package -e checkstyle:checkstyle -Dspring.profiles.active=dev'
            }
        }
        stage('Analysis') { 
            parallel { 
                stage('Code Coverage') {
                    steps {
                       checkstyle canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/checkstyle-result.xml', unHealthy: ''
                    }
                }
                stage('Test Coverage') {
                    steps {
                        jacoco maximumBranchCoverage: '90', maximumClassCoverage: '80', maximumComplexityCoverage: '70', maximumInstructionCoverage: '50', maximumLineCoverage: '65', maximumMethodCoverage: '70'
                        
                        //send surefire reports 
                        junit 'target/surefire-reports/*.xml'
                        
                        sh 'printenv'
                    }
                }
            }
        }
        stage('Upload') {
            steps {
                sh "cp target/*.war target/${params.artifactName}"
                sh "mvn -X deploy:deploy-file -DgroupId=${params.mavenGroupId} -DartifactId=${params.mavenArtifactId} -Dversion=${params.version}-SNAPSHOT -Dpackaging=${params.packaging} -Dfile=target/${params.artifactName} -DrepositoryId=snapshots -Durl=${params.artifactRepositoryUrl}"     
            }
        }
        stage('Deploy') {
            steps {
                sh "/root/info-scripts/info-client.sh ${params.destination} ${params.artifactRepositoryUrl} ${params.mavenGroupId} ${params.mavenArtifactId} ${params.version} ${params.packaging} ${params.artifactName} ${params.artifactPath}"
            }
        }
    }
}
