#!/bin/bash

dir=`dirname $0`
cd "$dir/src/"
javac */*.java
java view.Mastermind_View

