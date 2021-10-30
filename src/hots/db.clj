(ns hots.db)

(def banned-heroes #{"Abathur" "Cho" "Gall" "Cho'gall" "Zeratul" "Nova" "Tassadar" "The Lost Vikings"})

(def all-available? false)

(def set-ups
  [[{:role "Assassin" :type "Melee"} {:role "Assassin" :type "Melee"}]
   [{:role "Assassin" :type "Ranged"} {:role "Assassin" :type "Melee"}]
   [{:role "Assassin" :type "Ranged"} {:role "Assassin" :type "Ranged"}]
   [{:role "Support"} {:role "Assassin" :type "Melee"}]
   [{:role "Support"} {:role "Assassin" :type "Ranged"}]
   [{:role "Warrior"} {:role "Assassin" :type "Melee"}]
   [{:role "Warrior"} {:role "Assassin" :type "Ranged"}]

   ;[{:role "Assassin" :type "Melee"} {:role "Assassin"} {:role "Assassin" :type "Ranged"}]
   [{:role "Support"} {:role "Assassin" :type "Ranged"} {:role "Assassin" :type "Ranged"}]
   [{:role "Support"} {:role "Assassin" :type "Ranged"} {:role "Assassin" :type "Melee"}]
   [{:role "Support"} {:role "Assassin" :type "Melee"} {:role "Assassin" :type "Melee"}]
   [{:role "Warrior"} {:role "Assassin" :type "Ranged"} {:role "Assassin" :type "Melee"}]
   [{:role "Warrior"} {:role "Assassin"} {:role "Assassin" :type "Ranged"}]
   [{:role "Warrior"} {:role "Assassin"} {:role "Assassin" :type "Melee"}]
   [{:role "Warrior"} {:role "Assassin"} {:role "Support"}]

   [{:role "Support"} {:role "Assassin" :type "Melee"} {:role "Assassin"} {:role "Specialist"}]
   [{:role "Support"} {:role "Assassin"} {:role "Assassin" :type "Ranged"} {:role "Specialist"}]
   [{:role "Warrior"} {:role "Support"} {:role "Assassin"} {:role "Specialist"}]
   [{:role "Warrior"} {:role "Support"} {:role "Assassin" :type "Melee"} {:role "Assassin"}]
   [{:role "Warrior"} {:role "Support"} {:role "Assassin"} {:role "Assassin" :type "Ranged"}]
   [{:role "Warrior"} {:role "Support"} {:role "Assassin" :type "Ranged"} {:role "Assassin" :type "Ranged"}]])

(def heroes-map
  {:Anton       #{"Artanis" "Butcher" "Cho" "Chromie" "E.T.C." "Gall" "Gazlowe" "Jaina" "Kerrigan" "Kharazim" "Leoric"
                  "Li Li" "Li-Ming" "Lunara" "Malfurion" "Muradin" "Murky" "Nova" "Raynor" "Samuro" "Stitches" "Tracer" "Tychus"
                  "Tyrande" "Valla"}
   :Bratus      #{"Anub'arak" "Arthas" "Brightwing" "Cho" "Chromie" "E.T.C." "Gall" "Gazlowe" "Jaina" "Kharazim" "Li Li"
                  "Li-Ming" "Lt. Morales" "Lunara" "Malfurion" "Muradin" "Nazeebo" "Raynor" "Rehgar" "Sgt. Hammer"
                  "Sonya" "Sylvanas" "Tassadar" "Tyrael" "Tyrande" "Uther" "Valla" "Zagara"}
   :Eugene      #{"Artanis" "Cho" "Chromie" "Diablo" "E.T.C." "Gall" "Jaina" "Johanna" "Li Li" "Li-Ming" "Malfurion"
                  "Muradin" "Murky" "Nova" "Raynor" "Sylvanas" "Tracer" "Tyrande" "Valla"}
   :Ruslan      #{"Abathur" "Anub'arak" "Artanis" "Chen" "Cho" "Diablo" "E.T.C." "Gall" "Illidan" "Johanna" "Kael'thas"
                  "Kharazim" "Li Li" "Lt. Morales" "Malfurion" "Muradin" "Murky" "Raynor" "Rehgar" "Rexxar"
                  "Sgt. Hammer" "Sonya" "The Lost Vikings" "Tychus" "Uther" "Valla" "Zeratul"}
   :Konstantine #{"Anub'arak" "Artanis" "Arthas" "Azmodan" "Butcher" "Cho" "Dehaka" "Diablo" "E.T.C." "Gall" "Gazlowe"
                  "Greymane" "Illidan" "Jaina" "Johanna" "Kael'thas" "Kerrigan" "Leoric" "Li Li" "Li-Ming" "Malfurion"
                  "Medivh" "Muradin" "Nazeebo" "Raynor" "Sylvanas" "Tassadar" "Thrall" "Tychus" "Uther" "Valla" "Xul"
                  "Zagara" "Zeratul"}
   :Shiron      #{"Cho" "E.T.C." "Gall" "Illidan" "Johanna" "Li Li" "Li-Ming" "Malfurion" "Muradin" "Raynor" "Sonya"
                  "Sylvanas" "Tracer" "Tyrael" "Valla" "Xul" "Valeera"}
   :Stepanov    #{"Anub'arak" "Arthas" "Azmodan" "Cho" "Diablo" "E.T.C." "Gall" "Gazlowe" "Illidan" "Johanna"
                  "Kharazim" "Leoric" "Li Li" "Lunara" "Malfurion" "Muradin" "Raynor" "Rehgar" "Sonya" "Tassadar"
                  "Tychus" "Tyrael" "Valla" "Xul"}
   :Vlad        #{"Abathur" "Alarak" "Anub'arak" "Artanis" "Arthas" "Azmodan" "Brightwing" "Butcher" "Chen" "Cho"
                  "Chromie" "Dehaka" "Diablo" "E.T.C." "Falstad" "Gall" "Gazlowe" "Greymane" "Illidan" "Jaina" 
                  "Johanna" "Kael'thas" "Kerrigan" "Kharazim" "Li Li" "Lt. Morales" "Li-Ming" "Lunara" "Malfurion"
                  "Muradin" "Murky" "Nazeebo" "Nova" "Raynor" "Rehgar" "Rexxar" "Sgt. Hammer" "Sylvanas" "Sonya" 
                  "Stitches" "Tassadar" "Thrall" "Tracer" "Tychus" "Tyrael" "Tyrande" "Uther" "Valla" "Xul" "Zagara"
                  "Zarya"}})

(def gamers (set (keys heroes-map)))
