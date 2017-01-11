delimiterIn = ' ';
headerlinesIn = 1;
A = importdata('~/edu/5DV153/assignments/project/matlab/QoS/QoS1prim1peer',delimiterIn,headerlinesIn);

id = A.data(:,1);
time = A.data(:,2);

plot(id(1:length(id)-1),time(1:length(id)-1));


