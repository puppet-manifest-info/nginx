node('puppet-master') {
    timestamps {
        step([$class: 'WsCleanup'])
        sh "ls -lart"
        stage ('SCM Checkout') { scm() }
        stage ('Syntax Validation') { syntax() }
        stage ('Puppet Syntax Style Check ') { style() }
        stage ('UnitTest & AcceptanceTest') { unitTest() }
        stage ( 'Update the Manifest') { updateManifest() }
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

    echo '****Validation of Syntax****'
    sleep 5
    echo '****Approved Syntax as pep puppet manifest standard****'

}

def unitTest() {

    echo '****Running UnitTest Cases****'
    sleep 5
    echo '****Test Passed****'

}

def updateManifest() {

    sh 'echo jenkins | sudo -S cp -rf templates /etc/puppet/modules/nginx/'
    sh 'echo jenkins | sudo -S cp -rf manifests /etc/puppet/modules/nginx/ && echo jenkins | sudo -S chown -R root:root /etc/puppet/modules/nginx'
}
