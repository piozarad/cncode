%Fanuc-Hitachi
*Zamienia kod z Fanuc ycm na sterowanie Hitachi

*Korektory bez zmian

*Bazy
G54.1/P%l
G54.1=>G54


*Cykle bez zmian



*Kody maszynowe
M12=>M47
M8=>M47



*Obroty Stolu
M130=>M60
M127=>M61
M128=>M62
KL>G30
KL>G28
G65 ^+ M60
KL>65



*Inne

*Blokowanie stolu przy obrocie
B%l^+M79
B%lv+M78




