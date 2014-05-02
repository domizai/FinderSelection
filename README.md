FinderSelection
===============

A native Java/Processing library which reads, sorts and filters Mac-Finder selections

By Dominique Schmitz (domizai.ch)



Example
=======

This example only fetches .java files from the current selection in the Finder in descending order and returns an array of strings.

    String [] mySelection = new FinderSelection().withExtentions("java").descending().getPaths();
    
This should produce something like this:

    [0] "/Users/path/to/file_03.java"
    [1] "/Users/path/to/file_02.java"
    [2] "/Users/path/to/file_01.java"
