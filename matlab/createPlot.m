delimiterIn = ' ';
headerlinesIn = 1;
% Read and plot QoS 
A = importdata('~/edu/5DV153/assignments/project/matlab/QoS/QoS1prim1peer',delimiterIn,headerlinesIn);

id = A.data(:,1);
time = A.data(:,2);

% UnOrdered QoS
%plot(id(1:length(id)-1),time(1:length(time)-1));

% Sort list 
sortedTime = sort(time(1:length(time)-1));


plot((1:length(sortedTime)),sortedTime);
fiftyPrctile = prctile(sortedTime,50);
ninetyfivePrctile = prctile(sortedTime,95);
ninetyninePrctile = prctile(sortedTime,99);

%strFifty = ['Median: ' num2str(fiftyPrctile)];
%strNinteyFive = ['
dim = [0.2 0.5 0.3 0.3];
strFifty = num2str(fiftyPrctile);
prctileStr = {strcat('Median: ',num2str(fiftyPrctile)), strcat('95 percentile: ', num2str(ninetyfivePrctile)),strcat('99 percentile: ', num2str(ninetyninePrctile))};
annotation('textbox',dim,'String',prctileStr,'FitBoxToText','on');