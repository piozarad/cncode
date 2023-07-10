%Okuma-Fanuc
*Zamienia kod ze sterowaniem okumy na sterowanie Fanuc Ycm *HB500

*Bazy
G15?H=>P
G15=>G54.1

*Korektory
/D%l
/H%l
/DA
/DB
/DC
/HA
/HB
/HC
G56v+G0 G43 H%t Z400
/G56
G41+D%t

*Cykle 
G71=>G0
G76?J=>Q
G76/I
G76/K
G284=>G84
M53+G98
M52+G99
/M52
/M53

*Kody maszynowe
M51=>M12
M279=>M41
M278=>M43

*Obroty Stolu
M60=>G65P4

*Inne
G4 ? P=>X
KL>VPLNO





