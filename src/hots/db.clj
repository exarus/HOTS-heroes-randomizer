(ns hots.db)

(def week-heroes #{"Gazlowe" "Kerrigan" "Leoric" "Muradin" "Raynor" "Uther" "Sonya" "Sylvanas" "Lt. Morales", "Illidan"})
(def banned-heroes #{"Cho" "Gall" "Zeratul" "Nova"})

; role: Assassin Warrior Support Specialist
; type: Ranged Melee

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
  {:Anton       #{"Artanis"
                  "Butcher"
                  "Cho"
                  "Gall"
                  "Gazlowe"
                  "E.T.C."
                  "Jaina"
                  "Kharazim"
                  "Kerrigan"
                  "Leoric"
                  "Li Li"
                  "Li-Ming"
                  "Lunara"
                  "Malfurion"
                  "Muradin"
                  "Murky"
                  "Nova"
                  "Raynor"
                  "Stitches"
                  "Tychus"
                  "Tyrande"
                  "Valla"}
   :Bratus      #{"Anub'arak"
                  "Brightwing"
                  "Cho"
                  "Gall"
                  "E.T.C."
                  "Gazlowe"
                  "Jaina"
                  "Li Li"
                  "Lt. Morales"
                  "Lunara"
                  "Malfurion"
                  "Muradin"
                  "Raynor"
                  "Sgt. Hammer"
                  "Sonya"
                  "Sylvanas"
                  "Tassadar"
                  "Tyrael"
                  "Tyrande"
                  "Uther"
                  "Valla"
                  "Zagara"}
   :Eugene      #{"Artanis"
                  "Cho"
                  "Gall"
                  "Diablo"
                  "E.T.C."
                  "Jaina"
                  "Johanna"
                  "Li Li"
                  "Malfurion"
                  "Muradin"
                  "Nova"
                  "Raynor"
                  "Tyrande"
                  "Sylvanas"
                  "Valla"}
   :Ismail      #{"Cho"
                  "Gall"
                  "Illidan"
                  "Raynor"
                  "Sylvanas"}
   :Ruslan      #{"Abathur"
                  "Artanis"
                  "Chen"
                  "Cho"
                  "Gall"
                  "Diablo"
                  "E.T.C."
                  "Illidan"
                  "Kael'thas"
                  "Kharazim"
                  "Li Li"
                  "Lt. Morales"
                  "Malfurion"
                  "Muradin"
                  "Murky"
                  "Raynor"
                  "Rexxar"
                  "Sgt. Hammer"
                  "Sonya"
                  "The Lost Vikings"
                  "Tychus"
                  "Valla"
                  "Zeratul"}
   :Konstantine #{"Anub'arak"
                  "Artanis"
                  "Arthas"
                  "Azmodan"
                  "Butcher"
                  "Cho"
                  "Gall"
                  "Greymane"
                  "Diablo"
                  "E.T.C."
                  "Illidan"
                  "Jaina"
                  "Johanna"
                  "Kerrigan"
                  "Li Li"
                  "Malfurion"
                  "Muradin"
                  "Raynor"
                  "Tassadar"
                  "Thrall"
                  "Tychus"
                  "Sylvanas"
                  "Kael'thas"
                  "Uther"
                  "Leoric"
                  "Valla"
                  "Zagara"
                  "Zeratul"}
   :Shiron      #{"Cho"
                  "Gall"
                  "E.T.C."
                  "Illidan"
                  "Li Li"
                  "Malfurion"
                  "Muradin"
                  "Raynor"
                  "Sonya"
                  "Sylvanas"
                  "Tyrael"
                  "Valla"}
   :Stepanov    #{"Anub'arak"
                  "Azmodan"
                  "Cho"
                  "Gall"
                  "Gazlowe"
                  "Diablo"
                  "E.T.C."
                  "Illidan"
                  "Johanna"
                  "Kharazim"
                  "Leoric"
                  "Li Li"
                  "Malfurion"
                  "Muradin"
                  "Raynor"
                  "Rehgar"
                  "Sonya"
                  "Tychus"
                  "Tassadar"
                  "Tyrael"
                  "Valla"}
   :Vlad        #{"Abathur"
                  "Anub'arak"
                  "Artanis"
                  "Chen"
                  "Cho"
                  "Gall"
                  "E.T.C."
                  "Falstad"
                  "Gazlowe"
                  "Illidan"
                  "Jaina"
                  "Johanna"
                  "Kael'thas"
                  "Kerrigan"
                  "Kharazim"
                  "Li Li"
                  "Malfurion"
                  "Muradin"
                  "Murky"
                  "Raynor"
                  "Sonya"
                  "Tassadar"
                  "Thrall"
                  "Tychus"
                  "Tyrael"
                  "Valla"
                  "Zagara"}})

(def gamers (set (keys heroes-map)))
