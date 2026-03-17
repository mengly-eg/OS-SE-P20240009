Student Name: EANG Mengly
Student ID: p20240009

Task1: 
Kernel: 6.17.0-19-generic
OS: Ubuntu SMP PREEMPT_DYNAMIC
Architecture: x86_64 (64-bit)
Distribution: Ubuntu 24.04.4 LTS

Task2: 
- By using the > we can print the output into a new file or if the file already exist it will overwritten the existed file
- To create an empty file we use touch command: touch <filename>.txt
- To write a file we'll user echo command: echo "file content" <filename>.txt
- To copy a file we use cp: cp <filename>.txt <copiedfile_name>.txt
- To move a file we use mv: mv <filename>.txt ~/path/
- To move rename a file we use mv: mv <old_name>.txt <new_name>.txt
- To remove a file we use rm: rm <name>.txt

Task3:
- remove is used to reomve only package while keeping the configuration
- purge is used to remove both package and configuretion

Task4:
- To run the program in the background use: &
- when the program is running use command: ps to list the currently running program

Task5:
- The multitasking enviroment is having 2 sleep process running in the background and a python3 http web server available while running in the background

Task6:
- Based on the command output we can say it is running on virtual machine as it said vmware and in the lscpu it showed hypervisor vendor: vmware which indicated it is indeed a virtual machine