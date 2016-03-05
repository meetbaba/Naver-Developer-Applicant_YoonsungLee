#!/usr/bin/python

import sys
import os

from subprocess import call

projectRoot = '..'
scriptName  = os.path.basename(__file__)

if not 'VAP_BUILDSYSTEM_ROOT' in os.environ:
   print >> sys.stderr, \
          'The VAP build system python modules do not appear to be on the ' \
          'python include path. Ensure that the VAP_BUILDSYSTEM_ROOT ' \
          'environment variable is set correctly.'    
   sys.exit( 10 ) # ERR_NOBS 
   
   
script = os.path.join( os.environ['VAP_BUILDSYSTEM_ROOT'], 'scripts', 
            'project', scriptName )
    
if not os.path.isfile( script):
   print >> sys.stderr, \
          'File not found: ' + script
   sys.exit( 11 ) # ERR_NOSCRIPT

   
cmd =  [sys.executable, script]
cmd.extend(sys.argv[1:])
workDir = os.path.join( sys.path[0], projectRoot )
res = call( cmd, cwd=workDir )

sys.exit(res)