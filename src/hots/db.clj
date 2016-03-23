(ns hots.db)

(def banned-heroes #{"Cho" "Gall" "Cho'gall" "Zeratul" "Nova"})

(def set-ups
  [[{:role "Assassin" :type "Melee"}  {:role "Assassin" :type "Melee"}]
   [{:role "Assassin" :type "Ranged"} {:role "Assassin" :type "Melee"}]
   [{:role "Assassin" :type "Ranged"} {:role "Assassin" :type "Ranged"}]
   [{:role "Support"}                 {:role "Assassin" :type "Melee"}]
   [{:role "Support"}                 {:role "Assassin" :type "Ranged"}]
   [{:role "Warrior"}                 {:role "Assassin" :type "Melee"}]
   [{:role "Warrior"}                 {:role "Assassin" :type "Ranged"}]

   ;[{:role "Assassin" :type "Melee"} {:role "Assassin"} {:role "Assassin" :type "Ranged"}]
   [{:role "Support"} {:role "Assassin" :type "Ranged"}  {:role "Assassin" :type "Ranged"}]
   [{:role "Support"} {:role "Assassin" :type "Ranged"}  {:role "Assassin" :type "Melee"}]
   [{:role "Support"} {:role "Assassin" :type "Melee"}   {:role "Assassin" :type "Melee"}]
   [{:role "Warrior"} {:role "Assassin" :type "Ranged"}  {:role "Assassin" :type "Melee"}]
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
  {:Anton       #{"Artanis" "Butcher" "Cho" "E.T.C." "Gall" "Gazlowe" "Jaina" "Kerrigan" "Kharazim" "Leoric" "Li Li"
                  "Li-Ming" "Lunara" "Malfurion" "Muradin" "Murky" "Nova" "Raynor" "Stitches" "Tychus" "Tyrande"
                  "Valla"}
   :Bratus      #{"Anub'arak" "Brightwing" "Cho" "E.T.C." "Gall" "Gazlowe" "Jaina" "Li Li" "Li-Ming" "Lt. Morales"
                  "Lunara" "Malfurion" "Muradin" "Raynor" "Rehgar" "Sgt. Hammer" "Sonya" "Sylvanas" "Tassadar" "Tyrael"
                  "Tyrande" "Uther" "Valla" "Zagara"}
   :Eugene      #{"Artanis" "Cho" "Diablo" "E.T.C." "Gall" "Jaina" "Johanna" "Li Li" "Li-Ming" "Malfurion" "Muradin"
                  "Murky" "Nova" "Raynor" "Sylvanas" "Tyrande" "Valla"}
   :Ismail      #{"Cho" "Gall" "Illidan" "Raynor" "Sylvanas"}
   :Ruslan      #{"Abathur" "Anub'arak" "Artanis" "Chen" "Cho" "Diablo" "E.T.C." "Gall" "Illidan" "Kael'thas"
                  "Kharazim" "Li Li" "Lt. Morales" "Malfurion" "Muradin" "Murky" "Raynor" "Rehgar" "Rexxar"
                  "Sgt. Hammer" "Sonya" "The Lost Vikings" "Tychus" "Uther" "Valla" "Zeratul"}
   :Konstantine #{"Anub'arak" "Artanis" "Arthas" "Azmodan" "Butcher" "Cho" "Diablo" "E.T.C." "Gall" "Greymane"
                  "Illidan" "Jaina" "Johanna" "Kael'thas" "Kerrigan" "Leoric" "Li Li" "Li-Ming" "Malfurion" "Muradin"
                  "Raynor" "Sylvanas" "Tassadar" "Thrall" "Tychus" "Uther" "Valla" "Xul" "Zagara" "Zeratul"}
   :Shiron      #{"Cho" "E.T.C." "Gall" "Illidan" "Johanna" "Li Li" "Li-Ming" "Malfurion" "Muradin" "Raynor" "Sonya"
                  "Sylvanas" "Tyrael" "Valla" "Xul"}
   :Stepanov    #{"Anub'arak" "Arthas" "Azmodan" "Cho" "Diablo" "E.T.C." "Gall" "Gazlowe" "Illidan" "Johanna"
                  "Kharazim" "Leoric" "Li Li" "Lunara" "Malfurion" "Muradin" "Raynor" "Rehgar" "Sonya" "Tassadar"
                  "Tychus" "Tyrael" "Valla" "Xul"}
   :Vlad        #{"Abathur" "Anub'arak" "Artanis" "Chen" "Cho" "E.T.C." "Falstad" "Gall" "Gazlowe" "Illidan" "Jaina"
                  "Johanna" "Kael'thas" "Kerrigan" "Kharazim" "Li Li" "Li-Ming" "Malfurion" "Muradin" "Murky" "Raynor"
                  "Sonya" "Tassadar" "Thrall" "Tychus" "Tyrael" "Valla" "Zagara"}})

(def gamers (set (keys heroes-map)))
