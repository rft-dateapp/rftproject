using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;
using System.Runtime.Serialization;

namespace Pub_n_Fun.Models
{
    [DataContract]
    public class CustomerOpinion
    {
        private string opinion = "";
        private double rating;
        private string customerName;
        [Key]
        private int customerOpinionID;
        private int pubID;
        
        public CustomerOpinion(string opinion, double rating, string customerName, int customerOpinionID)
        {
            this.opinion = opinion;
            this.rating = rating;
            this.customerName = customerName;
            this.customerOpinionID = customerOpinionID;
        }

        [DataMember]
        public string Opinion
        {
            get => opinion;

            set => opinion = value;
        }

        [DataMember]
        public double Rating
        {
            get => rating;

            set => rating = value;
        }

        [DataMember]
        public string CustomerName
        {
            get => customerName;

            set => customerName = value;
        }

        [DataMember]
        public int CustomerOpinionID {
            get => customerOpinionID;

            set => customerOpinionID = value;
        }

        [DataMember]
        public int PubID
        {
            get => pubID;

            set => pubID = value;
        }
    }
}