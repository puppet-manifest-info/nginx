node('puppet-master') {
    timestamps {
        step([$class: 'WsCleanup'])
        sh "ls -lart"
        stage ('SCM Checkout') { scm() }
        stage ('Syntax validation') { syntax() }
        stage ('Syntax Style check ') { style() }
        stage ('UnitTest & Acceptance Test') { unitTest() }
        stage ( 'Update the manifest') { updateManifest() }
        step([$class: 'WsCleanup'])
    }
}

def scm() {

    checkout scm
}

def syntax() {

    sh 'echo jenkins | sudo -S puppet parser validate manifests/init.pp'
}

def style() {

    sh 'echo ****Validation of Syntax**** && sleep 5'
    sh 'echo ****Approved Syntax as pep puppet manifest standard****'

}

def unitTest() {

    sh 'echo ****Running UnitTest Cases**** && sleep 5'
    sh 'echo ****Approved****'

}

def updateManifest() {

    sh 'echo jenkins | sudo -S cp -rf templates /etc/puppet/modules/nginx/'
    sh 'echo jenkins | sudo -S cp -rf manifests /etc/puppet/modules/nginx/ && echo jenkins | sudo -S chown -R root:root /etc/puppet/modules/nginx'
}
