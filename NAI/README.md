## Spis Tre�ci
+ [Zadanie 1 - Tabela pa�stwa](#zadanie-1---tabela-pa�stw)
+ [Zadanie 2 - Delta algorithm](#zadanie-2---delta-algorithm)

---

## Zadanie 1 - Tabela pa�stw
## Zadanie 2 - Delta algorithm



Program wczytuje dane z plik�w tekstowych. Zak�adamy, ze:
� Pierwszy argument programu to plik z danymi treningowymi, drugi z danymi testowymi.
� Trzeci argument programyu to atrybut decyzyjnt, kt�ry chcemy rozr�nia� od pozosta�ych.
� Atrybut decyzyjny znajduje si� w ostatniej kolumnie w plikach tekstowych.
� Wszystkie atrybuty poza decycyjnym s� numeryczne.
Nast�pnie algorytmem delty trenujemy perceptron, kt�ry rozr�nia klas� podan� jako trzeci argument pozosta�ych klas (na danych treningowych).
Testujemy go potem na danych ze zbioru testowego.
Jako wynik wypisuje liczb� prawid�owo zaklasyfikowanych przyk�ad�w oraz dok�adno�� eksperymentu wyra�on� w procentach.
Program umo�liwia wielokrotne r�czne wpisanie wektora atrybut�w i wypisuje dla takiego wektora czy nale�y do danej klasy.