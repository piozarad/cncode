%Fanuc-Okuma
*Zamienia kod z Fanuc ycm na sterowanie Okumy


*Korektory
/D%l
H%l=>HA DA



*Bazy
G54/P%l
G54.1/P%l
G54+H1
G55+H2
G56+H3
G57+H4
G58+H5
G59+H6
G54=>G15 
G55=>G15 
G56=>G15 
G57=>G15 
G58=>G15 
G59=>G15 

*Wczytanie korekcji dlugosciowej
G43=>G56

*Cykle 
G76?Q=>J
G98^+G71 Z400
G98=>M53
G99=>M54



*Kody maszynowe
M12=>M51
M29/S%l
/M29
M41=>M279
M43=>M278
M99=>M30


*Obroty Stolu
M130=>M60
M127=>M60
M128=>M60
KL>G30
KL>G28



*Inne
G4?X=>P
/%



