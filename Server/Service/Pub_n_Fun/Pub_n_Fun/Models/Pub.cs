using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace Pub_n_Fun.Models
{
    [DataContract]
    public class Pub
    {
        private double customerOverallRatings = 0.00;
        private List<CustomerOpinion> customerOpinions = new List<CustomerOpinion>();
        private string name;
        [Key]
        private int pubID;
        private string address;

        public Pub(string name, int pubID, string address, List<CustomerOpinion> customerOpinions = null, double customerOverallRatings = 0)
        {
            this.customerOverallRatings = customerOverallRatings;
            this.customerOpinions = customerOpinions;
            this.name = name;
            this.pubID = pubID;
            this.address = address;
        }

        [DataMember]
        public double CustomerOverallRatings
        {
            get => customerOverallRatings;

            set => customerOverallRatings = value;
        }

        [DataMember]
        public List<CustomerOpinion> CustomerOpinions
        {
            get => customerOpinions;

            set => customerOpinions = value;
        }

        [DataMember]
        public string Name
        {
            get => name;

            set => name = value;
        }

        [DataMember]
        public int PubID
        {
            get => pubID;

            set => pubID = value;
        }

        [DataMember]
        public string Address
        {
            get => address;

            set => address = value;
        }
    }
}