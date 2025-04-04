<h1>Websocket Distributed Sewage Treatment Simulation</h1>
Projekt realizuje rozproszony system aplikacji dokonujących symulacji pracy firmy wywożącej nieczystości poprzez interakcje zachodzące między podsystemami, komunikującymi się za pomocą gniazd TCP/IP.
<br>
<img alt="AppView" src="https://github.com/Lukasz2004/websocket-distributed-simulation/blob/master/github/websocket-distributed-simulation.png?raw=true" align="center">
Projekt zrealizowany w ramach zajęć laboratoryjnych „Języki Programowania”, Politechnika Wrocławska, semestr zimowy 2024/2025. 
<br>
<h3>Użyte narzędzia:</h3>
<ul>
  <li>Java, ver. 17</li>
  <li>JavaFX, ver. 21.0.6</li>
</ul>

<h2>Pierwsze kroki</h2>
<h3>Wymagania:</h3>
<ul>
  <li>Java, ver. 17</li>
</ul>
<h3>Uruchomienie symulacji</h3>
Aby uruchomić symulacje należy kolejno uruchomić poszczególne aplikacje należące do systemu. W tym celu należy uruchomić odpowiadającą klasę „-Controller.java” znajdującą się w pakiecie odpowiadającym nazwą do typu aplikacji. Następnie, każdą aplikacje trzeba skonfigurować, nadając jej m.in. numer portu wystawianego do komunikacji i inne parametry poszczególne do typu aplikacji.
<br>
Uwaga! Używane przez aplikacje porty w przypadku uruchamianiu ich na maszynie lokalnej nie mogą się powtarzać
<br>
<h4>Sugerowane użycie portów:</h4>
<ul>
  <li>SewagePlantApp: 7001</li>
  <li>OfficeApp: 7000</li>
  <li>TankerApp: 7010-7099</li>
  <li>HouseApp: 7100+</li>
</ul>
<br>
Uwaga! Znaczenie ma kolejność uruchamiania poszczególnych podsystemów aplikacji. Należy kolejno dokonywać inicjalizacji:
<ul>
  <li>jednego SewagePlantApp</li>
  <li>przynajmniej jednego OfficeApp</li>
  <li>przynajmniej jednego TankerApp</li>
  <li>dowolnej ilości HouseApp</li>
</ul>
<h2>Opis agentów symulacji:</h2>
<ul>
  <li>Dom (HouseApp)
    <ul>
        <li>reprezentuje dom jednorodzinny, który nie jest podłączony do sieci kanalizacyjnej. Wytwarzane w nim nieczystości gromadzone są w przydomowym szambie, które co jakiś czas musi być opróżnione. </li>
        <li>Dom wysyła zamówienie na usługę do Biura, gdy szambo wypełni się do poziomu alarmowego. Realizacją takiej usługi zajmuje się bezpośrednio Cysterna.</li>
    </ul>
  </li>
  <li>Biuro (OfficeApp)
    <ul>
        <li>reprezentuje biuro firmy świadczącej usługi wywozu nieczystości. Przyjmuje zamówienie na usługi od Domów, zleca ich wykonanie Cysternom. </li>
        <li>korzysta z interfejsu Oczyszczalni, by pozyskać informację o sumarycznej objętości przywiezionych tam nieczystości przez poszczególne Cysterny oraz by się rozliczyć za nieczystości przywiezione przez poszczególne Cysterny.</li>
    </ul>
  </li>
  <li>Cysterna (TankerApp)
    <ul>
        <li>reprezentuje cysternę, która rejestruje się w Biurze. Przyjmuje zlecenia wykonania usługi od Biura. Wypompowuje nieczystości z szamba przy Domu w ramach wykonywania usługi. Wywozi nieczystości do Oczyszczalni. Zgłasza do Biura gotowość przyjęcia zlecenia; </li>
    </ul>
  </li>
    <li>Oczyszczalnia (SewagePlantApp)
    <ul>
        <li>reprezentuje oczyszczalnię, w której Cysterny zostawiają nieczystości </li>
    </ul>
  </li>
</ul>
<h2>Uwagi końcowe</h2>
Ikony aplikacji pozyskane zostały z serwisu "Flaticon"
