using Pub_n_Fun.Cache;
using Pub_n_Fun.Error;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using Pub_n_Fun.Models;

namespace Pub_n_Fun
{
    public class PubnFunCore : IPubnFunCore
    {
        private static List<Pub> Pubs = new List<Pub>();

        public void AddOpinion(CustomerOpinion OpinionToBeAdded)
        {
            try
            {
                Pubs.Find(p => p.PubID == OpinionToBeAdded.PubID).CustomerOpinions.Add(OpinionToBeAdded);
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public void AddPub(Pub PubToBeAdded)
        {
            try
            {
                Pubs.Add(PubToBeAdded);
            }
            catch(Exception e)
            {
                throw e;
            }
        }

        public void DeleteOpinionByID(string opinionID)
        {
            try
            {
                int tmp;
                int.TryParse(opinionID, out tmp);

                foreach(Pub pub in Pubs)
                {
                    pub.CustomerOpinions.Remove(pub.CustomerOpinions.Find(p => p.CustomerOpinionID == tmp));
                }
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public void DeletePubByID(string pubID)
        {
            try
            {
                int tmp;
                int.TryParse(pubID, out tmp);

                Pubs.Remove(Pubs.Find(p => p.PubID == tmp));
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public List<CustomerOpinion> GetAllOpinionListAboutPubByID(string pubID)
        {
            try
            {
                int tmp;
                int.TryParse(pubID, out tmp);

                return Pubs.Find(p => p.PubID == tmp).CustomerOpinions;
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public List<Pub> GetAllPubList()
        {
            return Pubs;
        }

        public CustomerOpinion GetCustomerOpinion(string opinionID)
        {
            try
            {
                int tmp;
                int.TryParse(opinionID, out tmp);

                foreach (Pub pub in Pubs)
                {
                    if (pub.CustomerOpinions.Any(p => p.CustomerOpinionID == tmp))
                    {
                        return pub.CustomerOpinions.Find(p => p.CustomerOpinionID == tmp);
                    }
                }

                return null;
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public Pub GetPubByID(string pubID)
        {
            try
            {
                int tmp;
                int.TryParse(pubID, out tmp);

                return Pubs.Find(p => p.PubID == tmp);
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public string GetSystemDate(string token)
        {
            try
            {
                if (CacheManager.ValidateToken(token))
                {
                    return DateTime.Now.ToString();
                }
                else
                {
                    throw new InvalidTokenException("Invalid given token at GetSystemDate");
                }
            }
            catch(Exception e)
            {
                throw e;
            }
        }

        public void UpdateOpinion(CustomerOpinion OpinionToBeUpdated)
        {
            throw new NotImplementedException();
        }

        public void UpdatePub(Pub PubToBeUpdated)
        {
            throw new NotImplementedException();
        }
    }
}
