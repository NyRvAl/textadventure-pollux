using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace TextAdventureAdminWPF
{
    public class AdventureGameModel
    {
        [JsonPropertyName("id")]
        public long Id { get; set; }

        [JsonPropertyName("name")]
        public string Name { get; set; }

        [JsonPropertyName("ratingSummedUp")]
        public long RatingSummedUp { get; set; }

        [JsonPropertyName("amountRating")]
        public long AmountRating { get; set; }

        [JsonPropertyName("available")]
        public bool Available { get; set; }

    }
}
